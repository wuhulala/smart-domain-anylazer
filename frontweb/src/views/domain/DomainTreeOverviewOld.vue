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
            G6.registerNode(
                'tree-node',
                {
                    drawShape: function drawShape(cfg, group) {
                        const rect = group.addShape('rect', {
                            attrs: {
                                fill: '#3ab882',
                                stroke: '#fff',
                            },
                            name: 'rect-shape',
                        });
                        const content = cfg.name.replace(/(.{19})/g, '$1\n');
                        const text = group.addShape('text', {
                            attrs: {
                                text: content,
                                x: 0,
                                y: 0,
                                textAlign: 'left',
                                textBaseline: 'middle',
                                fill: '#fff',
                            },
                            name: 'rect-shape',
                        });
                        const bbox = text.getBBox();
                        const hasChildren = cfg.children && cfg.children.length > 0;
                        if (hasChildren) {
                            group.addShape('marker', {
                                attrs: {
                                    x: bbox.maxX + 6,
                                    y: bbox.minX + bbox.height / 2 - 6,
                                    r: 6,
                                    symbol: COLLAPSE_ICON,
                                    stroke: '#fff',
                                    lineWidth: 2,
                                },
                                name: 'collapse-icon',
                            });
                        }
                        rect.attr({
                            x: bbox.minX - 4,
                            y: bbox.minY - 6,
                            width: bbox.width + (hasChildren ? 26 : 8),
                            height: bbox.height + 12,
                        });
                        return rect;
                    },
                },
                'single-node',
            );

            const width = document.getElementById('container').scrollWidth;
            const height = document.getElementById('container').scrollHeight || 800;
            const graph = new G6.TreeGraph({
                renderer: 'svg',
                container: 'container',
                width,
                height,
                modes: {
                    default: [
                        {
                            type: 'collapse-expand',
                            onChange: function onChange(item, collapsed) {
                                const data = item.get('model');
                                const icon = item.get('group').find(element => element.get('name') === 'collapse-icon');
                                if (collapsed) {
                                    icon.attr('symbol', EXPAND_ICON);
                                } else {
                                    icon.attr('symbol', COLLAPSE_ICON);
                                }
                                data.collapsed = collapsed;
                                return true;
                            },
                        },
                        'drag-canvas',
                        'zoom-canvas',
                    ],
                },
                defaultNode: {
                    type: 'tree-node',
                    anchorPoints: [
                        [0, 0.5],
                        [1, 0.5],
                    ],
                },
                defaultEdge: {
                    type: 'cubic-horizontal',
                    style: {
                        stroke: '#8699a3',
                        lineWidth: 1,
                    },
                },
                layout: {
                    type: 'compactBox',
                    direction: 'LR',
                    getId: function getId(d) {
                        return d.id;
                    },
                    getHeight: function getHeight() {
                        return 16;
                    },
                    getWidth: function getWidth() {
                        return 16;
                    },
                    getVGap: function getVGap() {
                        return 20;
                    },
                    getHGap: function getHGap() {
                        return 80;
                    },
                },
            });
            DomainService.findDomainTree(result => {
                let data = result.tree;
                G6.Util.traverseTree(data, function (item) {
                    item.id = item.name;
                    item.type = 'tree-node';
                });
                console.error(data);
                graph.data(data);
                graph.render();
                graph.fitView();
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