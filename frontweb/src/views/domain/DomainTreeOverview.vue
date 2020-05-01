<template>
    <div id="container" style="border: 1px solid #eee; width: 100%">

    </div>
</template>


<script>

    import G6 from '@antv/g6';
    import DomainService from "../../api/domain/DomainService";

    const COLLAPSE_ICON = function COLLAPSE_ICON(x, y, r) {
        return [
            ['M', x, y],
            ['a', r, r, 0, 1, 0, r * 2, 0],
            ['a', r, r, 0, 1, 0, -r * 2, 0],
            ['M', x + 2, y],
            ['L', x + 2 * r - 2, y],
        ];
    };
    const EXPAND_ICON = function EXPAND_ICON(x, y, r) {
        return [
            ['M', x, y],
            ['a', r, r, 0, 1, 0, r * 2, 0],
            ['a', r, r, 0, 1, 0, -r * 2, 0],
            ['M', x + 2, y],
            ['L', x + 2 * r - 2, y],
            ['M', x + r, y - r + 2],
            ['L', x + r, y + r - 2],
        ];
    };


    export default {
        name: "DomainOverview",
        mounted() {
            let that = this;
            const nodeStyle = {
                size: 30, // 节点大小
                // ...                 // 节点的其他配置
                // 节点样式配置
                style: {
                    fill: '#3ab882', // 节点填充色
                    stroke: '#ffffff', // 节点描边色
                    lineWidth: 1, // 节点描边粗细
                },
                // 节点上的标签文本配置
                labelCfg: {
                    // 节点上的标签文本样式配置
                    style: {
                        fill: '#ffffff', // 节点标签文字颜色
                        fontSize: 8,
                    },
                },
            };
            const width = document.getElementById('container').scrollWidth;
            const height = document.getElementById('container').scrollHeight || 800;
            const graph = new G6.TreeGraph({
                renderer: 'svg',
                container: 'container',
                width,
                height,
                linkCenter: true,
                modes: {
                    default: [
                        'drag-canvas',
                        'zoom-canvas',
                    ],
                },
                defaultNode: {
                    size: 30, // 节点大小
                    // ...                 // 节点的其他配置
                    // 节点样式配置
                    style: {
                        fill: '#3ab882', // 节点填充色
                        stroke: '#ffffff', // 节点描边色
                        lineWidth: 1, // 节点描边粗细
                    },
                    // 节点上的标签文本配置
                    labelCfg: {
                        // 节点上的标签文本样式配置
                        style: {
                            fill: '#ffffff', // 节点标签文字颜色
                            fontSize: 8,
                        },
                    },
                },
                defaultEdge: {
                    style: {
                        stroke: '#8699a3',
                    },
                },
                layout: {
                    type: 'dendrogram',
                    direction: 'RL',
                    getId: function getId(d) {
                        return d.id;
                    },
                    getHeight: () => {
                        return 26;
                    },
                    getWidth: () => {
                        return 26;
                    },
                    getVGap: () => {
                        return 10;
                    },
                    getHGap: () => {
                        return 15;
                    },
                    radial: true,
                },
            });

            DomainService.findDomainTree(result => {
                let data = result.tree;
                G6.Util.traverseTree(data, function (item) {
                    item.oriId = item.id;
                    item.id = item.name;
                });
                graph.node(function (node) {
                    return {
                        label: node.name,
                    };
                });
                graph.data(data);
                graph.render();
                graph.fitView();


                graph.on('node:mouseenter', function (evt) {
                    const node = evt.item;
                    const model = node.getModel();
                    model.oriLabel = model.label;
                    model.oriLabelCfg = model.labelCfg;
                    model.oriStyle = model.style;
                    model.oriSize = model.size;
                    graph.setItemState(node, 'hover', true);
                    graph.updateItem(node, {
                        label: model.id,
                        size: 55, // 节点大小
                        style: {
                            fill: '#ffffff', // 节点填充色
                            stroke: '#3ab882', // 节点描边色
                            lineWidth: 1, // 节点描边粗细
                        },
                        // 节点上的标签文本配置
                        labelCfg: {
                            // 节点上的标签文本样式配置
                            style: {
                                fill: '#3ab882', // 节点标签文字颜色
                                fontSize: 10,
                            },
                        },
                    });
                });

                graph.on('node:mouseleave', function (evt) {
                    const node = evt.item;
                    const model = node.getModel();
                    graph.setItemState(node, 'hover', false);
                    graph.updateItem(node, {
                        label: model.oriLabel,
                        labelCfg: model.oriLabelCfg,
                        style: model.oriStyle,
                        size: model.oriSize,
                    });
                });

                graph.on('node:dblclick', evt => {
                    const node = evt.item;
                    const item = node.getModel();
                    debugger
                    const detailId = item.code;
                    let url = that.$router.resolve({path: `/domain/detail/${detailId}`})
                    window.open(url.href, '_blank')
                });

            })

        },
        data() {
            return {
                activeKey: ['1'],
                domain_links: [{
                    "title": "大数据",
                    "children": [
                        {
                            "title": "Flink",
                            "page": [
                                {
                                    "avatar": "http://flinkhadoop:8081/assets/images/flink.svg",
                                    "title": "管理控制台",
                                    "url": "http://flinkhadoop:8081"
                                }
                            ],
                        }, {
                            "title": "Hbase",
                            "page": [
                                {
                                    "avatar": "http://flinkhadoop:16010/static/hbase_logo_small.png",
                                    "title": "Hasbe Master",
                                    "url": "http://flinkhadoop:16010"
                                }
                            ],
                        }
                        , {
                            "title": "Hadoop",
                            "page": [
                                {
                                    "avatar": "http://flinkhadoop:8088/static/hadoop-st.png",
                                    "title": "namenode浏览器",
                                    "url": "http://flinkhadoop:50070"
                                },
                                {
                                    "avatar": "http://flinkhadoop:8088/static/hadoop-st.png",
                                    "title": "Yarn管理控制台",
                                    "url": "http://flinkhadoop:8088"
                                }
                            ],
                        }

                    ]
                }]
            }
        }
    }
</script>

<style scoped>

</style>