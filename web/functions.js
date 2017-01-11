/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function Function()
{
 var v1=document.getElementById("opt1").checked;
 var v2=document.getElementById("opt2").checked;
 var v3=document.getElementById("opt3").checked;
 var v4=document.getElementById("opt4").checked;
 if(v1||v2||v3||v4)
 {
     
 }
 else
 {
     alert("Please select any 1 of 4 options");
     
     //return false;
 }
  
}
function preventBack()
{ 
    window.history.forward(1); 
}
 setTimeout("preventBack()", 0);
 window.onunload = function () { null };
        