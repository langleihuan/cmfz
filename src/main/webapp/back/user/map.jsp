<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; UTF-8" %>
<script type="text/javascript">
    $(function () {
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('userMap'));
        var option = {
            title: {
                text: '用户分布图',
                subtext: '纯属虚构',
                left: 'center'
            },
            tooltip: {
                trigger: 'item'
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data: ['用户']
            },
            visualMap: {
                left: 'left',
                top: 'bottom',
                text: ['高', '低'],           // 文本，默认为数值文本
                calculable: true
            },
            toolbox: {
                show: true,
                orient: 'vertical',
                left: 'right',
                top: 'center',
                feature: {
                    mark: {show: true},
                    dataView: {show: true, readOnly: false},
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            series: [

            ]
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
        $.get("${pageContext.request.contextPath}/user/queryByArea",function (data) {
            myChart.setOption({
                series:[{
                    name: '用户',
                    type: 'map',
                    mapType: 'china',
                    roam: false,
                    label: {
                        normal: {
                            show: false
                        },
                        emphasis: {
                            show: true
                        }
                    },
                    data: data
                }]
            })
        },"json")
    })

</script>


<div class="col-sm-10">
    <div id="userMap" style="width: 600px;height:400px;"></div>
</div>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->


