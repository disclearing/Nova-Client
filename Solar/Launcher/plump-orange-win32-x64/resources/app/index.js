const {
  app,
  BrowserWindow
} = require('electron');
const CryptoJS = require("crypto-js");
const {
  Client,
  Authenticator
} = require('minecraft-launcher-core');
const http = require('http');
const fs = require('fs');
var AdmZip = require('adm-zip');
const request = require('request');
const passPhrase = "PlumpOrangeOwnNova";
var updated = false;
let win;
const theConsole = console;

function getJSONP(url, success) {
    var ud = '_' + +new Date,
        script = document.createElement('script'),
        head = document.getElementsByTagName('head')[0] 
               || document.documentElement;
    window[ud] = function(data) {
        head.removeChild(script);
        success && success(data);
    };
    script.src = url.replace('callback=?', 'callback=' + ud);
    head.appendChild(script);
}


function windowClosed() {
  theConsole.log("Shutdown");
}

function windowReady() {
  theConsole.log("Ready");
  fs.readFile('./info.json', 'utf8', (err, jsonString) => {
      if (err) {
          console.log("File read failed:", err)
          return
      }
      const config = JSON.parse(jsonString);
      version = config.version;
  });
}

function accountSelected() {

}

function update() {
  console.log("Needs Updated");
  document.getElementById("launch-txt").innerText = "UPDATING";
  document.getElementById("loading").style.display = "none";

  const file = fs.createWriteStream("./1.7.10.zip");
  let stream = request({
          uri: 'http://192.95.4.92/1.7.10.zip',
          gzip: true
      })
      .pipe(file)
      .on('finish', () => {
          console.log(`The file is finished downloading.`);
          var zip = new AdmZip("./1.7.10.zip");
          zip.extractAllTo( /*target path*/ "./mc/versions/", /*overwrite*/ true);
          pgetJSONP("http://192.95.4.92/info.json", function(data) {
              console.log(data);
              fs.readFile('./info.json', 'utf8', (err, jsonString) => {
                  if (err) {
                      console.log("File read failed:", err)
                      document.getElementById("launch-txt").innerText = "LAUNCH";
                      document.getElementById("loading").style.display = "none";
                      return
                  }

                  const config = JSON.parse(jsonString);
                  config.version.oneseven = data.version.oneseven;
                  fs.writeFileSync('./info.json', JSON.stringify(config, null, 2));
                  fs.unlinkSync("./1.7.10.zip")
                  launch();
              });
          })
      })
      .on('error', (error) => {
          console.log(error);
          return;
      })
}

function launch() {
  //SETS LOADING
  document.getElementById("launch-txt").innerText = "";
  document.getElementById("loading").style.display = "block";

  //CHECKS FOR UPDATES
  getJSONP("http://192.95.4.92/info.json", function(data) {
      console.log(data);
      fs.readFile('./info.json', 'utf8', (err, jsonString) => {
          if (err) {
              console.log("File read failed:", err)
              document.getElementById("launch-txt").innerText = "LAUNCH";
              document.getElementById("loading").style.display = "none";
              return
          }

          const config = JSON.parse(jsonString);

          if (data.version.oneseven != config.version.oneseven) {
              update();
          }
      });
  })
  //ONE INSTANCE CHECKER
  if (document.getElementById("launch-txt").innerText == "READY" || document.getElementById("launch-txt").innerText == "" || document.getElementById("launch-txt").innerText == "UPDATING") {
      document.getElementById("launch-txt").innerText = "LAUNCH";
      document.getElementById("loading").style.display = "none";
      return;
  }

  //READS CONFIG AND LAUNCHES
  fs.readFile('./info.json', 'utf8', (err, jsonString) => {
      if (err) {
          console.log("File read failed:", err)
          document.getElementById("launch-txt").innerText = "LAUNCH";
          document.getElementById("loading").style.display = "none";
          return
      }
      const config = JSON.parse(jsonString);

      //CHECKS FOR SELECTED ACCOUNT
      if (config.selected == "" || config.accounts[config.selected] == null) {
          setTimeout(document.getElementById("launch-txt").innerText = "NO ACC", 2000);
          document.getElementById("launch-txt").innerText = "LAUNCH";
          document.getElementById("loading").style.display = "none";
          return;
      }

      //PARSES TOKEN
      let cached = CryptoJS.AES.decrypt(config.accounts[config.selected].cachedInfo, passPhrase).toString(CryptoJS.enc.Utf8);
      let details = cached.split(":");

      //GETS LAUNCH DETAILS AND SETS CONFIG
      let auth = Authenticator.getAuth(details[0], details[1]);
      let opts = {
          clientPackage: null,
          authorization: auth,
          root: "./mc",
          timeout: 60000,
          version: {
              number: "1.7.10",
              type: "release",
              custom: "Solar 1.7.10"
          },
          memory: {
              max: config.ram,
              min: 1
          }
      }

      //LAUNCH
      const launcher = new Client();
      launcher.launch(opts);

      //CLOSE LISTENER
      launcher.on('close', (e) => {
          document.getElementById("launch-txt").innerText = "LAUNCH";
          document.getElementById("loading").style.display = "none";
          updated = false;
      });

      //DEBUG
      launcher.on('debug', (e) => theConsole.log(e.toString('utf-8')));

      //CONSOLE OUTPUT
      launcher.on('data', (e) => {
          theConsole.log(e.toString('utf-8'));
          if (!updated) {
              updated = true;
              document.getElementById("launch-txt").innerText = "READY";
              document.getElementById("loading").style.display = "none";
          }
      });

      //ERRORS
      launcher.on('error', (e) => {
          theConsole.log(e.toString('utf-8'));
      });
  });
  return;
}

function login() {
  emailIn = document.getElementById("email");
  passwordIn = document.getElementById("password");

  if (emailIn.value.toString() == "" || !emailIn.value.toString().includes("@")) {
      theConsole.log("Invalid Email");
      return;
  }
  if (passwordIn.value.toString() == "") {
      theConsole.log("Empty Password");
      return;
  }


  var email = emailIn.value.toString();
  var password = passwordIn.value.toString();

  const requestObject = {
      url: "https://authserver.mojang.com" + "/authenticate",
      json: {
          agent: {
              name: "Minecraft",
              version: 1
          },
          username: email,
          password: password,
          requestUser: true
      }
  };

  request.post(requestObject, function(error, response, body) {
      if (error) resolve(error);
      if (!body.selectedProfile || body.selectedProfile.paid == false || body.selectedProfile.suspended == true) {
          console.log("Invalid Login!");
          return;
      } else {
          console.log(body);
          fs.readFile('./info.json', 'utf8', (err, jsonString) => {
              const config = JSON.parse(jsonString);
              var newCache = CryptoJS.AES.encrypt(email + ":" + password, passPhrase).toString();
              config.accounts[body.selectedProfile.id] = {
                  displayName: body.selectedProfile.name,
                  cachedInfo: newCache
              };
              config.selected = body.selectedProfile.id;
              document.getElementById("pfp").src = "https://visage.surgeplay.com/face/" + config.selected;
              document.getElementById("accountName").innerText = config.accounts[config.selected].displayName;

              if (!document.getElementById(config.selected)) {
                  var account = document.createElement("div");
                  account.className = "account-select";
                  account.id = config.selected;
                  account.addEventListener("click", (e) => selectAccount(event));
                  var accountButton = document.createElement("button");
                  var accountJson = config.accounts[config.selected];
                  accountButton.innerText = accountJson.displayName;
                  account.appendChild(accountButton);
                  accountSelect.appendChild(account);
              }
              resetAccountPopup();
              fs.writeFileSync('./info.json', JSON.stringify(config, null, 2));

          });
      }
  });



}

function resetAccountPopup() {

  //Close Switch Account / Login Menu
  document.getElementById("switchAccountPopup").style.display = "none";
  document.getElementById("loginPopup").style.display = "none";
  //Open Account Menu
  document.getElementById("accountPopup").style.display = "block";
}

function createWindow() {
  // Create the browser window.
  win = new BrowserWindow({
      width: 1000,
      height: 800,
      icon: "Nova.png",
      title: "Solar Client",
      resizable: false,
      frame: false,
      transparent: true,
      autoHideMenuBar: true,
      hasShadow: false,
      fullscreenable: false,
      minimizable: false,
      webPreferences: {
          nodeIntegration: true
      }
  });
  //win.setMenu(null);
  win.setFullScreenable(false);
  win.setMaximizable(false);
  win.isResizable(false);
  win.loadFile("index.html");
  win.on("closed", windowClosed);
  win.webContents.on('new-window', function(e, url) {
      e.preventDefault();
      require('electron').shell.openExternal(url);
  });
  windowReady();
}

app.on('ready', createWindow)