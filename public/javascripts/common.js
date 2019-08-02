document.addEventListener("DOMContentLoaded", function(){
var list = document.getElementById("tickers-common").getElementsByTagName('tr');
for (var i = 0; i < list.length; i++) {
     list[i].addEventListener("click",function(){
     funcOnClick(event);
      });
  }
});


function funcOnClickExecBUtton() {
  console.log("EXECUTE BUTTON")
  var selectedTickersTr = document.getElementById("tickers-common").getElementsByClassName('tr-ticker-selected');
  tickersSelectedArray = [];
  for (var i = 0; i < selectedTickersTr.length; i++) {
     tickersSelectedArray[i] = selectedTickersTr[i].id.split('-')[3];
   };
  if (tickersSelectedArray.length!=0){
  console.log("Selected tickersID = "+tickersSelectedArray)
//---------------------------------------------------------------------------
    postAjax('/barsstat',{"tickersId":tickersSelectedArray}, function(data){
      console.log(data);
      document.getElementById("right-up").innerHTML = data;
    });
//---------------------------------------------------------------------------
  } else {
  console.log("There is no selected tickers.")
  }
}




function funcOnClick(event) {
      var tickerId  = event.currentTarget.id.split('-')[3];
      console.log("tickerId="+tickerId);

      if (event.currentTarget.className=="tr-ticker-selected"){
       event.currentTarget.className = "tr-ticker";
      } else {
       event.currentTarget.className = "tr-ticker-selected";
      }

      //change class ticker-selected,

//---------------------------------------------------------------
       /*ajax_get('/getbars/'+tickerId+'/'+widthSec+'/'+bPattCnt+'/'+bMaxTsEnd, function(data) {
         console.log("JSON data.rows="+ data["data"].length);
         document.getElementById("div-test").innerHTML = data;
         for (var i=0; i < data["data"].length; i++) {
           console.log("i=["+i+"]  ts_end="+data["data"][i][0]+"  DateTime="+ timeConverter(data["data"][i][0]) +" c="+data["data"][i][1]);
         }
         paintJQBarsGraph(data,widthSec);
        });
       */
//---------------------------------------------------------------
}


function ajax_get(url, callback) {
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            console.log('responseText:' + xmlhttp.responseText);
            try {
                var data = JSON.parse(xmlhttp.responseText);
            } catch(err) {
                console.log(err.message + " in " + xmlhttp.responseText);
                return;
            }
            callback(data);
        }
    };
    xmlhttp.open("GET", url, true);
    xmlhttp.send();
}


function postAjax(url, data, success) {
    var xhr = window.XMLHttpRequest ? new XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHTTP");
    xhr.open('POST', url);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function() {
        if (xhr.readyState>3 && xhr.status==200) { success(xhr.responseText); }
    };
    var params = JSON.stringify(data);
    xhr.send(params);
    return xhr;
}


