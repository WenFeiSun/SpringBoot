layui.use(['layer','form'], function(){
    var form = layui.form
        ,layer = layui.layer;

    //监听提交
    form.on('submit(loginForm)', function(data){
        $.ajax({
            url:'/submitLoginForm',
            //contentType:'application/json',
            type:'post',
            /*data:JSON.stringify({
                userName:$('#userName').val(),
                password:$('#password').val(),
            }),*/
            /*dataType:'json',*/
            data:data.field,
            //验证用户名是否可用
            success:function(res){
                if (res.code== 200) {
                    layer.msg(res.msg);
                    window.location.href="./main.html";
                } else {
                    layer.msg(res.msg);
                }
            },
        })
        return false;
    });
});