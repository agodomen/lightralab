dateUI = function () {
    $("#date").datepicker();
}



/*$(document).ready(function() {

 // get the action filter option item on page load
 var $filterType = $('#filterOptions li.active a').attr('class');

 // get and assign the ourHolder element to the
 // $holder varible for use later
 var $holder = $('ul.ourHolder');

 // clone all items within the pre-assigned $holder element
 var $data = $holder.clone();

 // attempt to call Quicksand when a filter option
 // item is clicked
 $('#filterOptions li a').click(function(e) {
 // reset the active class on all the buttons
 $('#filterOptions li').removeClass('active');

 // assign the class of the clicked filter option
 // element to our $filterType variable
 var $filterType = $(this).attr('class');
 $(this).parent().addClass('active');

 if ($filterType == 'all') {
 // assign all li items to the $filteredData var when
 // the 'All' filter option is clicked
 var $filteredData = $data.find('li');
 }
 else {
 // find all li elements that have our required $filterType
 // values for the data-type element
 var $filteredData = $data.find('li[data-type=' + $filterType + ']');
 }

 // call quicksand and assign transition parameters
 $holder.quicksand($filteredData, {
 duration: 800,
 easing: 'easeInOutQuad'
 });

 $('.ourHolder li.trigger').find('strong').trigger('mouseenter');

 return false;
 });

 });*/
//this is for ajax submit the form
commitForm = function (urload, div) {
	
}

loadHtml = function (urlLoad, div) {
    urlLoad = systemGetRootPath() + urlLoad;
    if (div == '') {
        //window.location.href=urlLoad;
        window.location.assign(urlLoad);
    } else {
        /*
         * location.hostname 返回 web 主机的域名
         location.pathname 返回当前页面的路径和文件名
         location.port 返回 web 主机的端口 （80 或 443）
         location.protocol 返回所使用的 web 协议（http:// 或 https://）
         */
        $(div).load(urlLoad, {}, function () {
        });
    }
}


/**
 * http://localhost:8083/proj/
 */
systemGetRootPath = function getRootPath() {
    //获取当前网址，如： http://localhost:8083/proj/meun.jsp  
    var curWwwPath = window.document.location.href;  
    //获取主机地址之后的目录，如： proj/meun.jsp  
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);  
    //获取主机地址，如： http://localhost:8083  
    var localhostPath = curWwwPath.substring(0, pos);  
    //获取带"/"的项目名，如：/proj  
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    return (localhostPath + projectName);
}  

