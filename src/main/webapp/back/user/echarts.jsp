<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; UTF-8" %>


    <div class="col-sm-10">
        <div id="main" style="width: 600px;height:400px;"></div>
    </div>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->

<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: 'ECharts 入门示例'
        },
        tooltip: {},
        legend: {
            data:['男','女']
        },
        xAxis: {
            data: ["1天","7天","30天","1年"]
        },
        yAxis: {},
        series: [],
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
    // Ajax异步数据回显

    $.post("${pageContext.request.contextPath}/user/queryBy",function (data) {
        myChart.setOption({
            series:[
                {
                    name: '男',
                    type: 'bar',
                    data: [data.man1,data.man7,data.man30,data.man365],
                },{
                    name: '女',
                    type: 'bar',
                    data: [data.woman1,data.woman7,data.woman30,data.woman365],
                }
            ]
        })
    },"json")
</script>

<script>
    var goEasy = new GoEasy({
        host:'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
        appkey: "BC-eb29f9235303415c90afd5cb262f9f85", //替换为您的应用appkey
    });
    goEasy.subscribe({
        channel: "cmfz", //替换为您自己的channel
        onMessage: function (message) {
            // 手动将 字符串类型转换为 Json类型
            //var data = JSON.parse(message.content);
            // alert("Channel:" + message.channel + " content:" + message.content);
            $.post("${pageContext.request.contextPath}/user/queryBy",function (data) {
            myChart.setOption({
                series:[
                    {
                        name: '男',
                        type: 'bar',
                        data: [data.man1,data.man7,data.man30,data.man365],
                    },{
                        name: '女',
                        type: 'bar',
                        data: [data.woman1,data.woman7,data.woman30,data.woman365],
                    }
                ]
            })
        },"json")
    }
    });
</script>
