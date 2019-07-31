
    $(function(){
        $('#tickers').multiSelect();
    });


function ajax_get(url, callback) {
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            console.dir('responseText:' + xmlhttp.responseText);
            try {
                var data = JSON.parse(xmlhttp.responseText);
            } catch(err) {
                console.dir(err.message + " in " + xmlhttp.responseText);
                return;
            }
            callback(data);
        }
    };
    xmlhttp.open("GET", url, true);
    xmlhttp.send();
}


document.addEventListener("DOMContentLoaded", function(){
var list = document.getElementsByClassName('tr-ticker');
for (var i = 0; i < list.length; i++) {
     list[i].addEventListener("click",function(){
     funcOnClick(event);
     }
    );
  }
});


function funcOnClick(event) {
      var tickerId  = event.currentTarget.id.split('-')[3];
      console.dir("tickerId="+tickerId);
      //---------------------------------------------------------------
         /*
         ajax_get('/getbars/'+tickerId+'/'+widthSec+'/'+bPattCnt+'/'+bMaxTsEnd, function(data) {
         console.dir("JSON data.rows="+ data["data"].length);
         document.getElementById("div-test").innerHTML = data;
         for (var i=0; i < data["data"].length; i++) {
           console.dir("i=["+i+"]  ts_end="+data["data"][i][0]+"  DateTime="+ timeConverter(data["data"][i][0]) +" c="+data["data"][i][1]);
         }
         paintJQBarsGraph(data,widthSec);
        });
        */
      //---------------------------------------------------------------
}

