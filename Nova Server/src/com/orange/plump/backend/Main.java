package com.orange.plump.backend;

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.orange.plump.backend.server.Server;
import org.apache.commons.lang3.StringEscapeUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {

	private static Server server;
	private static ClientThread clientThread;
	private static HashMap<String, String> currentTokens = new HashMap<String, String>();
	private static HashMap<String, Long> currentActive = new HashMap<String, Long>();
	private static HashMap<String, Long> currentTimes = new HashMap<String, Long>();

	public static void main(String[] args) throws IOException {
		clientThread = new ClientThread();
		clientThread.start();
		server = new Server(20090);
		server.start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						List<String> remove = new ArrayList<String>();

						for (String key : currentActive.keySet()) {
							long time = currentActive.get(key);
							if ((System.currentTimeMillis() - time) > 25000) remove.add(key);
						}

						for (String r : remove) {
							log("(https://namemc.com/profile/" + r + ")" + " closed Nova with token \"" + "hidden" + "\"\n");
							clientClose(r, currentTokens.get(r));
						}

						remove.clear();

						JSONObject config = getConfig();
						config.put("Count", currentActive.size());
						FileWriter file = new FileWriter("config.json");
						file.write(StringEscapeUtils.unescapeEcmaScript(config.toJSONString()));
						file.flush();

						JSONObject players = getPlayers();
						if (isNewDay()) {
							Iterator iterator = players.keySet().iterator();
							while (iterator.hasNext()) {
								JSONObject player = (JSONObject) players.get(iterator.next());
								JSONObject playtime = (JSONObject) player.get("playtime");
								playtime.remove("daily");
								playtime.put("daily", 0L);
							}
							file = new FileWriter("players.json");
							file.write(StringEscapeUtils.unescapeEcmaScript(players.toJSONString()));
							file.flush();
						}

						if (isNewWeek()) {
							Iterator iterator = players.keySet().iterator();
							while (iterator.hasNext()) {
								JSONObject player = (JSONObject) players.get(iterator.next());
								JSONObject playtime = (JSONObject) player.get("playtime");
								playtime.remove("weekly");
								playtime.put("weekly", 0L);
							}
							file = new FileWriter("players.json");
							file.write(StringEscapeUtils.unescapeEcmaScript(players.toJSONString()));
							file.flush();
						}



						Thread.currentThread().sleep(15000);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	private static boolean isNewDay() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(System.currentTimeMillis()));
		int current = cal.get(Calendar.DAY_OF_WEEK);
		cal.setTime(new Date(System.currentTimeMillis() - (35000)));
		int earlier = cal.get(Calendar.DAY_OF_WEEK);
		if (current != earlier) return true;
		else return false;
	}

	private static boolean isNewWeek() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(System.currentTimeMillis()));
		int current = cal.get(Calendar.DAY_OF_WEEK);
		cal.setTime(new Date(System.currentTimeMillis() - (35000)));
		int earlier = cal.get(Calendar.DAY_OF_WEEK);
		if (current == Calendar.MONDAY && earlier == Calendar.SUNDAY) return true;
		else return false;
	}

	private static synchronized JSONObject getConfig() throws IOException, ParseException {
		JSONParser parser = new JSONParser();
		return (JSONObject) parser.parse(new FileReader("config.json"));
	}

	private static synchronized JSONObject getPlayers() throws IOException, ParseException {
		JSONParser parser = new JSONParser();
		return (JSONObject) parser.parse(new FileReader("players.json"));
	}

	public static void log(String s) {
		try {
			PrintWriter out = new PrintWriter(new FileOutputStream("log.txt", true));
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy $:mm");
			LocalDateTime now = LocalDateTime.now();
			String amPm = " AM";
			int hour = now.getHour();
			if (hour > 11) amPm = " PM";
			if (hour > 12) hour = hour-12;
			out.write(dtf.format(now).replace("$", String.valueOf(hour)) + amPm + " > " + s);
			System.out.println(dtf.format(now).replace("$", String.valueOf(hour)) + amPm + " > " + s);
			out.close();
		} catch (FileNotFoundException e) {
			System.out.println("Failed to output to log file!");
			e.printStackTrace();
		}
	}

	private static synchronized void logPlayTime(String uuid, long time) {
		try {
			JSONObject playersJson = getPlayers();
			long all = 0, weekly = 0, daily = 0, laston;
			try {
				all = (long) ((JSONObject)((JSONObject)playersJson.get(uuid)).get("playtime")).get("all");
				weekly = (long) ((JSONObject)((JSONObject)playersJson.get(uuid)).get("playtime")).get("weekly");
				daily = (long) ((JSONObject)((JSONObject)playersJson.get(uuid)).get("playtime")).get("daily");
			} catch (Exception e) {

			}

			long period = System.currentTimeMillis() - time;
			all+=period;
			weekly+=period;
			daily+=period;
			laston=System.currentTimeMillis();

			JSONObject playtime = ((JSONObject)((JSONObject)playersJson.get(uuid)).get("playtime"));
			playtime.remove("all");
			playtime.put("all", all);
			playtime.remove("weekly");
			playtime.put("weekly", weekly);
			playtime.remove("daily");
			playtime.put("daily", daily);
			playtime.remove("laston");
			playtime.put("laston", laston);

			FileWriter file = new FileWriter("players.json");
			file.write(StringEscapeUtils.unescapeEcmaScript(playersJson.toJSONString()));
			file.flush();
			//log("(https://namemc.com/profile/" + uuid + ") Played for " + (period / 1000 / 60) + " minutes\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static String command(String ip, String message) {
		String response = "";
		//NOVACLIENT$PACKET LAUNCH uuid
		if (message.startsWith("NOVACLIENT$PACKET ")) {
			message = message.replace("NOVACLIENT$PACKET ", "");
			String command, args;
			command = message.split(" ")[0];
			args = message.replace(command + " ", "");

			if (command.equals("CANRUN")) {
				String uuid = args;
				response = isAllowedToLaunch(ip.replace("/", ""), uuid);
				if (response == "FALSE" ) {
					log("(https://namemc.com/profile/" + uuid + ")" + " tried running Nova, but has no access\n");
				} else if (response == "TRUE") {
					if (currentActive.containsKey(uuid)) {
						logPlayTime(uuid, currentActive.get(uuid));
						currentActive.replace(uuid, System.currentTimeMillis());
					}
				}
			} else if (command.equals("GETCAPES")) {
				response = getCapes();
			} else if (command.equals("GETCOSMETICS")) {
				response = getCosmetics(args);
			} else if (command.equals("LAUNCH")) { ;
				String uuid = args.split(":")[0];
				String token = args.split(":")[1];
				response = clientLaunch(uuid, token);
				log("(https://namemc.com/profile/" + uuid + ")" + " launched Nova with token \"" + "hidden" + "\"\n");
			} else if (command.equals("JOINSERVER")) {
				String name = args.split(":")[0];
				String uuid = args.split(":")[1];
				String serverIP = args.split(":")[2];
				log("(https://namemc.com/profile/" + uuid + ")" + " joined the server \"" + serverIP + "\"\n");
			} else if (command.equals("CHAT")) {
				String name = args.split(":")[0];
				String uuid = args.split(":")[1];
				String chat = args.split(":")[2];
				//Parse chat
			}  else if (command.equals("GETFEATURED")) {
				response = getFeatured();
			}


		}
		return response;
	}

	private static String clientLaunch(String uuid, String token) {
		String response = "";
		try {
			String isValid = httpRequest(new URL("https://authserver.mojang.com/validate"), json(token));
			if (isValid.equals("VALID")) response = "TRUE";
			else response =  "FALSE";
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (response != "FALSE") {

			try {
				JSONObject playersJSON = getPlayers();
				JSONObject player = null;
				try {
					player = (JSONObject) playersJSON.get(uuid);
				} catch (Exception e) {

				}
				if (player == null) createPlayer(uuid);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (currentTokens.containsKey(uuid)) {
				String tokenThere = currentTokens.get(uuid);
				if (!tokenThere.equals(token)) response = "FALSE";
			} else {
				currentTokens.put(uuid, token);
				if (currentActive.containsKey(uuid)) currentActive.remove(uuid);
				currentActive.put(uuid, System.currentTimeMillis());
				if (currentTimes.containsKey(uuid)) currentTimes.remove(uuid);
				currentTimes.put(uuid, System.currentTimeMillis());
			}

		}
		return response;
	}

	private static String clientClose(String uuid, String token) {
		if (currentActive.containsKey(uuid) && currentTokens.containsKey(uuid) && currentTimes.containsKey(uuid)) {
			String tokenThere = currentTokens.get(uuid);
			if (tokenThere.equals(token)) {
				currentTokens.remove(uuid);
				currentActive.remove(uuid);
				currentTimes.remove(uuid);
			}
		}
		return "TRUE";
	}

	@SuppressWarnings("unchecked")
	private static String isAllowedToLaunch(String ip, String uuid) {
		try {
			JSONObject players = getPlayers();
			JSONObject player = (JSONObject) players.get(uuid);

			JSONObject config = getConfig();
			JSONArray ipBlacklist = (JSONArray) config.get("IP Blacklist");
			String doWhitelist = (String) config.get("Whitelist");

			if ((boolean) (player.get("banned"))) {
				return "FALSE";
			}

			Iterator iterator = ipBlacklist.iterator();
			while (iterator.hasNext()) {
				String inBlack = (String) iterator.next();
				if (inBlack.equals(ip))
					return "FALSE";
			}



            if (doWhitelist.equals("Enabled"))
            	if ((boolean)(player.get("whitelisted"))) {
		            return "TRUE";
	            } else return "FALSE";
            else
            	return "TRUE";

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "FALSE";
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.println("Error was thrown at \'isAllowedToLaunch()\' IOEXCEPTION, trying method again.");
			return isAllowedToLaunch(ip, uuid);
		} catch (ParseException e) {
			//e.printStackTrace();
			System.out.println("Error was thrown at \'isAllowedToLaunch()\' PARSEEXCEPTION, trying method again.");
			return isAllowedToLaunch(ip, uuid);
		}
	}

	private static String getCapes() {
		JSONParser parser = new JSONParser();
		try {
			JSONObject config = (JSONObject) parser.parse(new FileReader("config.json"));
			JSONArray capes = (JSONArray) config.get("Capes");
			return capes.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	private static String getFeatured() {
		JSONParser parser = new JSONParser();
		try {
			JSONObject config = (JSONObject) parser.parse(new FileReader("config.json"));
			String servers = (String) config.get("Featured");
			return servers;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	private static String getCosmetics(String uuid) {
		try {
			JSONObject players = getPlayers();
			JSONObject player = null;
			try {
				player = (JSONObject) players.get(uuid);
			} catch (Exception e) {

			}
			if (player == null) return "none";
			JSONArray cosmeticsArray = (JSONArray) player.get("cosmetics");
			String cosmetics = "";
			Iterator iterator = cosmeticsArray.iterator();
			while (iterator.hasNext()) {
				String cosmetic = iterator.next().toString();
				if (iterator.hasNext()) cosmetics+=cosmetic + ";;";
				else cosmetics+=cosmetic;
			}
			if (cosmetics.equals("")) return "none";
			return cosmetics;
		} catch (Exception e) {
			e.printStackTrace();
			return "none";
		}
	}

	private static void createPlayer(String uuid) {
		try {
			JSONObject playersJSON = getPlayers();
			JSONObject player = null;
			try {
				player = (JSONObject) playersJSON.get(uuid);
			} catch (Exception e) {

			}
			if (player != null)
				return;

			JSONObject data = new JSONObject();
				data.put("whitelisted", false);
				data.put("banned", false);
				data.put("muted", false);
				data.put("discordid", "");
				data.put("cosmetics", new JSONArray());

				JSONArray ranks = new JSONArray();
				ranks.add("Default");
				data.put("ranks", ranks);

				JSONObject playtimeData = new JSONObject();
				playtimeData.put("all", 0L);
				playtimeData.put("daily", 0L);
				playtimeData.put("weekly", 0L);
				playtimeData.put("laston", 0L);
				data.put("playtime", playtimeData);

			playersJSON.remove(uuid);
			playersJSON.put(uuid, data);
			FileWriter file = new FileWriter("players.json");
			file.write(StringEscapeUtils.unescapeEcmaScript(playersJSON.toJSONString()));
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private static String json(String token){
        JSONObject json = new JSONObject();
        json.put("accessToken", token);
        return json.toJSONString();
    }

	private static String httpRequest(URL url, String tokenJson) throws Exception {
        byte[] contentBytes = tokenJson.getBytes("UTF-8");

        URLConnection connection = url.openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestProperty("Accept-Charset", "UTF-8");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Content-Length", Integer.toString(contentBytes.length));

        OutputStream requestStream = connection.getOutputStream();
        requestStream.write(contentBytes, 0, contentBytes.length);
        requestStream.close();

        if (((HttpURLConnection) connection).getResponseCode() == 204) {
        	return "VALID";
        } else {
        	return "INVALID";
        }

    }
}
