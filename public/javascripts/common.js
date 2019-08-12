//global variables
var isSingleTicker=0;

document.addEventListener("keydown", function(event) {
  const key = event.key;
   let addPrev = -1
   let addNext = 1
  var allTickersTrSelected = document.getElementById("tickers-common").getElementsByClassName('tr-ticker-selected').length;
  switch (key) { // change to event.key to key to use the above variable
    case "ArrowUp":
      //console.log("ArrowUp");
      if (isSingleTicker==1 && allTickersTrSelected==1) {
       var thisSelectedTicker = document.getElementById("tickers-common").getElementsByClassName('tr-ticker-selected')[0]
       let tickerId = thisSelectedTicker.id.split('-')[3];
       let prevTickerId = +tickerId + +addPrev
       var prevTr = document.getElementById("dict-ticker-row-"+prevTickerId)
       if (prevTr != null) {
        prevTr.className = 'tr-ticker-selected'
        thisSelectedTicker.className = 'tr-ticker'
        console.log("prev ticker is id = "+prevTr.id+" class = "+prevTr.className);
        funcOnClick(prevTr);
       }

      }
      break;
    case "ArrowDown":
      //console.log("ArrowDown");
      if (isSingleTicker==1 && allTickersTrSelected==1) {
       var thisSelectedTicker = document.getElementById("tickers-common").getElementsByClassName('tr-ticker-selected')[0]
       let tickerId = thisSelectedTicker.id.split('-')[3];
       let nextDownTickerId = +tickerId + +addNext
       var nextDownTr = document.getElementById("dict-ticker-row-"+nextDownTickerId)
       if (nextDownTr != null) {
        nextDownTr.className = 'tr-ticker-selected'
        thisSelectedTicker.className = 'tr-ticker'
        console.log("next ticker down is id = "+nextDownTr.id+" class = "+nextDownTr.className);
        funcOnClick(nextDownTr);
       }
      }
      break;
  }
});


/**/
document.addEventListener("DOMContentLoaded", function(){

if (document.getElementById("tickers-common") !== null) {
var list = document.getElementById("tickers-common").getElementsByTagName('tr');
for (var i = 0; i < list.length; i++) {
     list[i].addEventListener("click",function(){
      funcOnClick(event);
     });
     /* in case of each tr tickerCalcFailBws(list[i]); */
  }};

//once for all tickers for whole page
if (document.getElementById("tickers-common") !== null) {
 tickerCalcFailBwsA();
};

//add event listener for checkbos issingles
if (document.getElementById("issingles") !== null) {
const chboxIsSingle = document.getElementById("issingles")
  chboxIsSingle.addEventListener("change", (event) => {

   if (event.target.checked) {
    isSingleTicker = 1
    console.log("checked isSingleTicker="+isSingleTicker)
    document.getElementById("exec").style.visibility = 'hidden';
    document.getElementById("selalltickers").style.visibility = 'hidden';
    document.getElementById("unselalltickers").style.visibility = 'hidden';
    //unselect all tickers

    var allTickersTrSelected = document.getElementById("tickers-common").getElementsByClassName('tr-ticker-selected').length;
    if (allTickersTrSelected==1){
     funcOnClickExecButton();
    } else {
     funcOnClickUnSelAllButton();
    }

   } else {
    isSingleTicker = 0
    console.log("not checked isSingleTicker="+isSingleTicker)
    document.getElementById("exec").style.visibility = 'visible';
    document.getElementById("selalltickers").style.visibility = 'visible';
    document.getElementById("unselalltickers").style.visibility = 'visible';
   }

  });
};

});

function funcOnClick(event) {
      var obj =  (event.currentTarget == null) ? event  : event.currentTarget;
      var tickerId  = obj.id.split('-')[3];
      console.log("funcOnClick tickerId = "+tickerId+" isSingleTicker = "+isSingleTicker);
      tickerCalcFailBws(tickerId);
            if (isSingleTicker == 1) {
                console.log("Next ...");
                funcOnClickUnSelAllButton();
                console.log("select this ticker")
                obj.className = "tr-ticker-selected"
                funcOnClickExecButton();
            } else {
                event.currentTarget.className = (event.currentTarget.className == "tr-ticker-selected") ? "tr-ticker" : "tr-ticker-selected"
                var allTickersTrSelected = document.getElementById("tickers-common").getElementsByClassName('tr-ticker-selected').length;
                if (allTickersTrSelected==1){
                console.log("Multiselect and one selected.");
                funcOnClickExecButton();
                }
            }
}


/*
 This function calls when we click on tr with tickers.
*/
function tickerCalcFailBws(tickerid) {
    //console.log("tickerCalcFailBws for ticker="+tickerid)
     var thisTickerImg = document.getElementById("wimg-ticker-"+tickerid)
     if (thisTickerImg!=null){
       thisTickerImg.style.visibility = 'visible';
     }
     var tdFail  = document.getElementById("td-ticker-fail-bws-"+tickerid)
     var divFail = document.getElementById("span-ticker-fail-bws-"+tickerid)
     ajax_get('/bwsfailcnt/'+tickerid, function(data) {
       divFail.innerHTML = data.failcnt
       tdFail.className = (data.failcnt != "0") ? "td-ticker-fail" : "td-ticker"
       if (thisTickerImg!=null){
        thisTickerImg.style.visibility = 'hidden';
       }
      });
}

/*
 This function calls once, after page reloaded.
*/
function tickerCalcFailBwsA() {
     ajax_get('/bwsfailcnta', function(data) {
       /*
       console.log("data.bwsfailcnta="+ data);
       console.log("data.tickersFails="+ data.tickersFails);
       */
       var arrayData = data.tickersFails

       var listTickers = document.getElementById("tickers-common").getElementsByTagName('tr');
       //start from 1 to eliminate header TR
       for (var i = 1; i < listTickers.length; i++) {
       /*console.log("trid = "+listTickers[i].id)*/
        var tickerid = listTickers[i].id.split('-')[3]
        /*console.log("loop by listTickers, tickerid="+tickerid)*/

        var tdFail = document.getElementById("td-ticker-fail-bws-"+tickerid)
        var divFail = document.getElementById("span-ticker-fail-bws-"+tickerid)
        var thisTickerImg = document.getElementById("wimg-ticker-"+tickerid)
           if (thisTickerImg!=null){
             thisTickerImg.style.visibility = (thisTickerImg.style.visibility ? 'visible' : 'hidden');
           }

        for (var j = 0; j < arrayData.length; j++) {
          if (arrayData[j].tickerID == tickerid){
            if (arrayData[j].failcnt != "0") {
               divFail.innerHTML = arrayData[j].failcnt
               tdFail.className = "td-ticker-fail"
            }
          }
        }
        if (tdFail.className != "td-ticker-fail") {
         divFail.innerHTML = "0";
        }
       }
     }
  );
}


function funcOnClickExecButton() {
  console.log("EXECUTE BUTTON");
  var selectedTickersTr = document.getElementById("tickers-common").getElementsByClassName('tr-ticker-selected');
  tickersSelectedArray = [];
  for (var i = 0; i < selectedTickersTr.length; i++) {
     tickersSelectedArray[i] = selectedTickersTr[i].id.split('-')[3];
   };
  if (tickersSelectedArray.length!=0){
  console.log("Selected tickersID = "+tickersSelectedArray);
//---------------------------------------------------------------------------
    postAjax('/barsstat',{"tickersId":tickersSelectedArray}, function(data){
      //console.log(data);
      document.getElementById("div-bars-stats").innerHTML = data;
    });
//---------------------------------------------------------------------------
  } else {
  console.log("There is no selected tickers.");
  }
}

function funcOnClickReloadButton() {
  console.log("RELOAD BUTTON");
  document.location.reload(true);
}


function funcOnClickSelAllButton() {
  console.log("SELECT ALL BUTTON");
  if (isSingleTicker==0){
  var allTickerTr = document.getElementById("tickers-common").getElementsByTagName('tr');
    for (var i = 0; i < allTickerTr.length; i++) {
      //exclude header TR
      if (allTickerTr[i].classList.length != 0){
       allTickerTr[i].className = "tr-ticker-selected";
      }
    };
   }
}

/*
 Unselect all tickers if selected tickers count more then 1.
*/
function funcOnClickUnSelAllButton() {
  console.log("UNSELECT ALL BUTTON");
  var allTickerTr = document.getElementById("tickers-common").getElementsByTagName('tr');
  var allTickersTrSelected = document.getElementById("tickers-common").getElementsByClassName('tr-ticker-selected').length;
  console.log("funcOnClickUnSelAllButton allTickersTrSelected = "+allTickersTrSelected);
  //if (allTickersTrSelected > 1) {
    for (var i = 0; i < allTickerTr.length; i++) {
       //exclude header TR
       if (allTickerTr[i].classList.length != 0){
        allTickerTr[i].className = "tr-ticker";
       }
    };
   //}
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

/*===========================================================================================================*/

function funcOnClickTbar(elmnt) {



      console.log("Selected line TR id="+elmnt.id);
       var partsOfStr = elmnt.id.split('-');
         var bPattCnt  = 30;
         var tickerId  = partsOfStr[1];
         var widthSec  = partsOfStr[2];
         var pDateYear = partsOfStr[3];
         var pDateMon  = partsOfStr[4].padStart(2,'0');
         var pDateDay  = partsOfStr[5].padStart(2,'0')
         var fullDate  = pDateYear+"-"+pDateMon+"-"+pDateDay

          var thisTickerImg = document.getElementById("wimg-bws-"+tickerId)
          if (thisTickerImg != null){
            thisTickerImg.style.visibility = 'visible';
          }

         /*
         console.log("tickerId="+tickerId);
         console.log("widthSec="+widthSec);
         console.log("bPattCnt="+bPattCnt);
         console.log("bDdate="+fullDate);
         console.log("------------------------");
         */
         ajax_get('/getbars/'+tickerId+'/'+widthSec+'/'+bPattCnt+'/'+fullDate, function(data) {
         /*
         console.log("JSON data.rows="+ data["data"].length);
         document.getElementById("div-test").innerHTML = data;
         for (var i=0; i < data["data"].length; i++) {
           console.log("i=["+i+"] ts_end="+data["data"][i][0]+"  DateTime="+ timeConverter(data["data"][i][0]) +" c="+data["data"][i][1]);
         }
         */
          if (thisTickerImg != null){
            thisTickerImg.style.visibility = 'hidden';
          }

         paintJQBarsGraph(data,widthSec);
        });

}

function timeConverter(UNIX_timestamp){
  var a = new Date(UNIX_timestamp);
  var months = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
  var year = a.getFullYear();
  var month = months[a.getMonth()];
  var date = a.getDate();
  var hour = a.getHours();
  var min = a.getMinutes();
  var sec = a.getSeconds();
  var time = hour + ':' + min + ':' + sec ;//date + '.' + (a.getMonth()+1) + '.' + year + ' ' + hour + ':' + min + ':' + sec ;
  return time;
}

function paintJQBarsGraph(series,widthSec){
      //console.log("function paintJQBarsGraph");
		var options = {
		    grid: {
        		    hoverable: true,
        			clickable: true
        		  },
			lines: {
				show: true
			},
			points: {
				show: true
			},
			xaxis: {
				//tickDecimals: 0,
				//tickSize:     1000
				  minTickSize: [1, "minute"],
                  //min: (new Date("2018-08-13Z14:57:00")).getTime(),
                  //max: (new Date("2018-08-13Z15:05:00")).getTime(),
				  mode: "time",
                  timeformat: "%H:%M:%S"//"%d.%m.%Y %H:%M:%S"
			},
			yaxes:  { position: "right" }
		};

        //timeformats https://stackoverflow.com/questions/2507235/jquery-flot-xaxis-time

		var data = [];
		$.plot("#placeholder", data, options);
		// Fetch one series, adding to what we already have
		var alreadyFetched = {};

		// Push the new data onto our existing data array
		//console.log("series.label="+series.label)
		if (!alreadyFetched[series.label]) {
			alreadyFetched[series.label] = true;
			data.push(series);
		}
		$.plot("#placeholder", data, options);
}



$(function() {
$("<div id='tooltip'></div>").css({
			position: "absolute",
			display: "none",
			border: "1px solid #fdd",
			padding: "2px", "background-color": "#fee",
			opacity: 0.80
		}).appendTo("body");

function time(s) {
    return new Date(s * 1e3).toISOString().slice(-13, -5);
}

$("#placeholder").bind("plothover", function (event, pos, item) {
				var str = "(" + pos.x.toFixed(2) + ", " + pos.y.toFixed(2) + ")";
				$("#hoverdata").text(str);
				if (item) {
					var x = item.datapoint[0];
						y = item.datapoint[1];
					$("#tooltip").html(timeConverter(x) + "   -   " + y)
						.css({top: item.pageY+5, left: item.pageX+5})
						.fadeIn(200);
				} else {
					$("#tooltip").hide();
				}
		});
});



function ajax_get(url, callback) {
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            //console.log('responseText:' + xmlhttp.responseText);
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











