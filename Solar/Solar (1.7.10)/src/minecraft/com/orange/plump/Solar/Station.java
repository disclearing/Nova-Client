package com.orange.plump.Solar;

public enum Station {
	DASH_X(31, "Dash X", "Pop"), DASH_ALT(12, "Dash Alt", "Alternitive"), DASH_POP(17, "Dash Pop", "Pop"),
	DASH_HIP_HOP(90, "Dash Hip-Hop", "Hip-Hop"), DASH_DANCE(84, "Dash Dance", "Dance"), DASH_HITS(58, "Dash Hits", "Top Hits"),
	DASH_INDIE(32, "Dash Indie", "Indie"), DASH_R_AND_B(47, "Dash R&B", "R&B"), DASH_MIXTAPE(74, "Dash Mixtape", "Dubstep"),
	DASH_LATIN(48, "Dash Latin", "Latin"), TRILLER(81, "Triller", "Hip-Hop"), NATIVE_RYTHMS(18, "Native Rythms", "Hip-Hop"),
	INSOMNIAC(65, "Insomniac", "Electronic"), DASH_TALK(50, "Dash Talk", "Talk"), POP_FAMILY(94, "Pop Family", "Kids Pop"),
	GODS_HOUSE_OF_HIP_HOP(77, "GH Hip-Hop", "Gospel"), BIG3(86, "BIG3", "Hip-Hop"), THE_CITY(2, "The City", "Hip-Hop"),
	TAILGATE(35, "Tailgate", "Country"), THE_RANCH(13, "The Ranch", "Country"), WEST_COAST(78, "West Coast", "Hip-Hop"),
	SOUTHSIDE(95, "Southside", "Hip-Hop"), YOUNG_MONEY(82, "Young Money", "Hip-Hop"), ONE_AM(75, "1 AM", "Hip-Hop"),
	THE_CROSS(42, "The Cross", "Gospel"), THE_BRIDGE(44, "The Bridge", "Gospel"), CLASSIC_HIPHOP(11, "Classic HipHop", "Hip-Hop"),
	LAUGH_FACTORY(92, "Laugh Factory", "Comedy"), INDEPENDENT_GRIND(8, "Ind. Grind", "Hip-Hip"), VOICE_OF_REASON(72, "Voice Of Reason", "Talk"),
	Y2K(19, "Y2K", "Decades"), NINE_ONE(1, "90's", "Decades"), EIGHT_ONE(7, "80's", "Decades"),
	SEVEN_ONE(26, "70's", "Decades"), SIX_ONE(34, "60's", "Decades"), RATPACK(5, "Ratpack", "Decades"),
	DISCO_FEVER(4, "Disco Fever", "Decades"), SWING(80, "Swing", "Decades"), BEAT_JUNKIE(51, "Beat Junkie", "Hip-Hop"),
	DELICIOUS_VINYL(71, "Delicious Vinyl", "Hip-Hop"), KYLIE(49, "Kylie", "Pop"), FUSION(88, "Fusion", "Pop"),
	BREALTV(60, "BREALTV", "Hip-Hop"), ELECTRO_CITY(9, "Electro City", "Electronic"), MONSTERS_OF_ROCK(14, "Monsters Of Rock", "Rock"),
	GET_FAMILIAR(74, "Get Familiar", "Hip-Hop"), DASH_COMEDY(67, "Dash Comedy", "Comedy"), IDENTITY_ASIA(53, "Identity Asia", "World"),
	OVERDRIVE(73, "Overdrive", "Electronic"), ISLAND_BLOCK(64, "Island Block", "World"), DISCOVER(46, "Discover", "Pop"),
	LOUD(15, "LOUD", "Electronic"), JOHN_LENNON(52, "John Lennon", "Rock"), NOTHIN_BUT_NET(96, "Nothin' But Net", "Talk"),
	RINSE(70, "Rinse", "Electronic"), SDS_CADILLACC(16, "SD's Cadillacc", "R&B"), RUKUS_AVENUE(54, "Rukus Avenue", "World"),
	COOL(6, "Cool", "Jazz"), BUYGORE(30, "Buygore", "Electronic"), LA_PODEROSA(43, "La Poderosa", "Latin"),
	YG_PRESENTS(28, "YG Presents", "World"), LOVE_SONGS(66, "Love Songs", "Pop"), BOOMERANG(39, "Boomerang", "R&B"),
	CHURCH_OF_ROCK(21, "Church Of R&R", "Rock"), WAVES(55, "waves", "Electronic"), REGGAE_KING(79, "Reggae King", "World"),
	RAINBOW(41, "Rainbow", "Pop"), THE_BLUE_SPOT(59, "The Blue Spot", "Jazz"), MONSTERCAT(63, "Monstercat", "Electronic"),
	IMPERIO(27, "Imperio", "Latin"), CONCERTO(33, "Concerto", "Jazz"), PARADISE_CITY(20, "Paradise City", "Rock"),
	PURE_SOUL(36, "Pure Soul", "R&B"), SPOONY(91, "Spoony", "Talk"), FANTOM(45, "Fantom", "Electronic"),
	LA_ISLA(10, "La Isla", "Latin"), SUPER_FREAK(22, "Super Freak", "R&B"), THE_STRIP(24, "The Strip", "Rock"),
	CINESCORE(29, "Cinescore", "Jazz"), DESI(93, "Desi", "World"), MULTIPLAYER(57, "Multiplayer", "Talk"),
	SPARTAN(56, "Spartan", "All"), GRAVE_RAVE(68, "Grave Rave", "All"), MOONLIGHT(37, "Moonlight", "R&B");

	private int id;
	private String name, genre;

	Station(int id, String name, String genre) {
		this.id = id;
		this.name = name;
		this.genre = genre;
	}

	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getGenre() {
		return this.genre;
	}
}
