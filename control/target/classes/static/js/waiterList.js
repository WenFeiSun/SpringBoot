var laydate = layui.laydate;
var form = layui.form;
var laypage = layui.laypage;
var layer = layui.layer;
var table = layui.table;
var element = layui.element;
var currpage=1;    //设置分页默认首页
var pagesize=10 ;  //设置分页默认大小
var totalpage = 0;
var $ = layui.$;
$(function () {
    //加载列表的后端 url
    var tableOptions = {
        url: '/waiter/waiterList/', //请求地址
        iscurl: false,
        method: 'POST', //方式
        id: 'dataTable', //生成 Layui table 的标识 id，必须提供，用于后文刷新操作，笔者该处出过问题
        page: true,//是否分页
        success : function(data) {
            //执行一个laypage实例
            InitPage(data.count)
        },
    };
    //渲染下拉框
    form.render();
    //表初始化
    var createTable = function () {
        table.init('testaa', tableOptions);// table lay-filter
    };
    //表初始化
    createTable();

    //监听工具条
    table.on('tool(testaa)', function(obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值
        var tr = obj.tr; //获得当前行 tr 的DOM对象

        if(layEvent ==='update'){
            updateWaiter(data["code"]);
        } else if(layEvent ==='delete'){
            deleteWaiter(data["code"]);
        };
    });
});
function InitPage(total){
    //绑定分页
    totalpage = total ;
    var count =total;
    laypage.render({
        elem: 'page1',
        limits: [10, 15, 20, 30],
        limit: 10,
        layout: ['count', 'prev', 'page', 'next', 'limit', 'skip'],
        count: count ,//数据总数，从服务端得到
        jump: function(obj, first) {
            //obj包含了当前分页的所有参数，比如：
            //首次不执行
            if(!first) {
                currpage = obj.curr;
                pagesize = obj.limit;
                getData();
            }
        }
    });
};
//表格刷新方法（查询）
function reloadTable() {
    var userName = $('#userName').val();
    table.reload("dataTable", { //此处是上文提到的 初始化标识id
        where: {
            //key: { //该写法上文已经提到
            userName: userName
            //}
        }
    });
    //form.render("select");
};
//添加
function btnAdd() {
    layer.open({
        type: 1,
        title: '新增管理人员',
        shade: 0.4, //阴影度
        fixed : false,
        shadeClose: false,
        area: ['700px;', '400px;'], //窗体大小（宽,高）
        content: $("#addDialog"), //传入本层的窗口索引给同级的Iframe
        success: function(layero, index) {
            $("#addindex").val(index); //赋值给新增弹出层索引号，便于在内容保存时，自动关闭弹出层
        },
        end: function() {
            form.render();
            table.render();
            $("#addDialog").hide();
        }
    });
};
//新增保存
form.on("submit(addWaiter)",function(data){
    //提交数据
    $.ajax({
        url : '/waiter/saveWaiter',
        type : 'POST',
        async : false,
        data:{
            "userName":$("#wuserName").val(),
            "tel":$("#tel").val(),
            "age":$("#age").val(),
            "sex":$("#sex").val(),
            "idCardType":$("#idCardType").val(),
            "idCard":$("#idCard").val(),
            "status":$("#status").val(),
            "des":$("#des").val()
        },
        success : function(data) {
            if(data.code==200){
                //提示语句
                layer.msg(data.msg);
                //关闭当前layer
                layer.close($("#addindex").val());
                //清空form表单数据
                $("#addForm")[0].reset();
                $("#addDialog").hide();
                //重新获取数据
                reloadTable();
            }else{
                layer.msg(data.msg);
            }
        },
        error: function(e){
            layer.alert(e.error);
        },
        beforeSend: null,
        complete: null
    });
    return false;
});
//打开编辑框
function updateWaiter(data){
    layer.open({
        type: 1,
        title: '编辑管理人员',
        shade: 0.4, //阴影度
        fixed : false,
        shadeClose: false,
        area: ['700px;', '400px;'], //窗体大小（宽,高）
        content: $("#addDialog"), //传入本层的窗口索引给同级的Iframe
        success: function(layero, index) {
            $("#addindex").val(index); //赋值给新增弹出层索引号，便于在内容保存时，自动关闭弹出层
            showEditDialog(data);
        },
        end: function() {
            form.render();
            table.render();
            $("#addDialog").hide();
        }
    });
};
function showEditDialog(data) {
    $.ajax({
        url:"/waiter/updateWaiter",
        type:"post",
        data:{"code":data},
        success:function (res) {
            if(res.data.sex=="0"){
                $("#sex").val("男");
            }else if(res.data.sex=="1"){
                $("#sex").val("女");
            };
            if(res.data.idCardType=='01'){
                $("#idCardType").val("身份证");
            }else if(res.data.idCardType=="02"){
                $("#idCardType").val("暂住证");
            }else if(res.data.idCardType=="03"){
                $("#idCardType").val("港澳通行证");
            };
            if(res.data.status=='0'){
                $("#status").val("删除");
            }else if(res.data.status=="1"){
                $("#status").val("拉黑");
            }else if(res.data.status=="2"){
                $("#status").val("正常");
            };
            $("#wuserName").val(res.data.userName);
            $("#tel").val(res.data.tel);
            $("#age").val(res.data.age);
            $("#idCard").val(res.data.idCard);
            $("#des").val(res.data.des);

        }
    })
};
function deleteWaiter(data){
    $.ajax({
       url:"/waiter/deleteWaiter",
       type:"post",
       data:{
           "code":data
       },
        success:function (res) {
            layer.msg(res.msg);
            reloadTable();
        }
    });
}
