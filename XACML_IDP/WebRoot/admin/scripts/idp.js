function getRootPath(){
        var strFullPath=window.document.location.href;
        var strPath=window.document.location.pathname;
        var pos=strFullPath.indexOf(strPath);
        var prePath=strFullPath.substring(0,pos);
        var postPath=strPath.substring(0,strPath.substr(1).indexOf('/')+1);
        return(prePath+postPath);
        }


function checklogin() {
    if(checkform() == false){
        return;
    }
    else{
        var data;
        data = $('#loginform').serialize();
        $.post(getRootPath()+"/admin/loginAction.action",data,display_welcom,'json');
    }
};
function display_welcom(data, textStatus, jqXHR){
    if(data.islogin == 'true'){
        $("#welcomeword").text(data.username);
        //$("#logincontainer").fadeOut("slow");
        $("#logincontainer").slideUp(2000);

        if(data.action == 'submitresponse'){
            acsForm.SAMLResponse.value = data.samlResponse;
            acsForm.action = data.acsUrl;
            acsForm.submit();
        }else if (data.action == 'welcome'){
        $("#welcomeword").text(data.username);
        
        $("#welcome").show();
        $("#welcomeword").animate({
            fontSize:'60px'
        }, 2000 );

        } 
    }
    else{
        alert("passwords error");
        login.password.value = "";
    }
}
		function checkform(){
			if((login.username.value!="")&&(login.password.value!="")){
				return true;
			}
			else {
				alert("用户名密码不能为空！");
				return false;
			}
		}

function getArgs() { 
    var args = {};
        var query = location.search.substring(1);
         // Get query string
    var pairs = query.split("&"); 
                    // Break at ampersand
     for(var i = 0; i < pairs.length; i++) {
            var pos = pairs[i].indexOf('=');
             // Look for "name=value"
            if (pos == -1) continue;
                    // If not found, skip
                var argname = pairs[i].substring(0,pos);// Extract the name
                var value = pairs[i].substring(pos+1);// Extract the value
                value = decodeURIComponent(value);// Decode it, if needed
                args[argname] = value;
                        // Store as a property
        }
    return args;// Return the object 
 }
