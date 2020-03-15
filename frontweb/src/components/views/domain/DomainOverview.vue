<template>
    <div>
        <a-collapse v-model="activeKey">
            <a-collapse-panel v-for="data in domain_links" :header="data.title" key="1">
                <a-list
                        :grid="{ gutter: 16, column: 4 }"
                        :dataSource="data.children"
                >
                    <a-list-item slot="renderItem" slot-scope="item">
                        <a-card :title="item.title">
                            <router-link slot="extra" :to="{ name: 'DomainDetail', params: { id: item.title }}">more</router-link>
                            <a-list
                                    itemLayout="horizontal"
                                    :dataSource="item.page"
                            >
                                <a-list-item slot="renderItem" slot-scope="item, index">
                                    <a-list-item-meta

                                    >
                                        <a slot="title" style="cursor:pointer;color: #2fbbff" target="_blank"
                                           :href="item.url">{{item.title}}</a>
                                        <a-avatar slot="avatar" :src="item.avatar"/>
                                    </a-list-item-meta>
                                </a-list-item>
                            </a-list>
                        </a-card>
                    </a-list-item>
                </a-list>
            </a-collapse-panel>

        </a-collapse>
    </div>
</template>


<script>
    export default {
        name: "DomainOverview",
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