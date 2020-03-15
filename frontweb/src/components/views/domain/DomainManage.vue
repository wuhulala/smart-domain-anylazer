<template>
    <div>
        <a-row>
            <a-col :span="8">
                <a-tree
                        checkable
                        @expand="onExpand"
                        :expandedKeys="expandedKeys"
                        :autoExpandParent="autoExpandParent"
                        v-model="checkedKeys"
                        @select="onSelect"
                        :selectedKeys="selectedKeys"
                        :treeData="treeData"
                />
            </a-col>
            <a-col :span="16">
                <a-table :columns="columns" :dataSource="data">
                    <a slot="name" slot-scope="text" href="javascript:;">{{text}}</a>
                    <span slot="customTitle"><a-icon type="smile-o"/> Name</span>
                    <span slot="tags" slot-scope="tags">
                        <a-tag
                                v-for="tag in tags"
                                :color="tag==='loser' ? 'volcano' : (tag.length > 5 ? 'geekblue' : 'green')"
                                :key="tag"
                        >
                            {{tag.toUpperCase()}}
                        </a-tag>
                </span>
                        <span slot="action" slot-scope="text, record">
                    <a href="javascript:;">Invite 一 {{record.name}}</a>
                    <a-divider type="vertical"/>
                    <a href="javascript:;">Delete</a>
                    <a-divider type="vertical"/>
                    <a href="javascript:;" class="ant-dropdown-link"> More actions <a-icon type="down"/> </a>
                </span>
                </a-table>
            </a-col>
        </a-row>
    </div>
</template>


<script>
    const columns = [
        {
            dataIndex: 'name',
            key: 'name',
            slots: {title: 'customTitle'},
            scopedSlots: {customRender: 'name'},
        },
        {
            title: 'Age',
            dataIndex: 'age',
            key: 'age',
        },
        {
            title: 'Address',
            dataIndex: 'address',
            key: 'address',
        },
        {
            title: 'Tags',
            key: 'tags',
            dataIndex: 'tags',
            scopedSlots: {customRender: 'tags'},
        },
        {
            title: 'Action',
            key: 'action',
            scopedSlots: {customRender: 'action'},
        },
    ];

    const data = [
        {
            key: '1',
            name: 'John Brown',
            age: 32,
            address: 'New York No. 1 Lake Park',
            tags: ['nice', 'developer'],
        },
        {
            key: '2',
            name: 'Jim Green',
            age: 42,
            address: 'London No. 1 Lake Park',
            tags: ['loser'],
        },
        {
            key: '3',
            name: 'Joe Black',
            age: 32,
            address: 'Sidney No. 1 Lake Park',
            tags: ['cool', 'teacher'],
        },
    ];
    const treeData =  [{
        "title":"大数据",
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
            },{
                "title": "Hbase",
                "page": [
                    {
                        "avatar": "http://flinkhadoop:16010/static/hbase_logo_small.png",
                        "title": "Hasbe Master",
                        "url": "http://flinkhadoop:16010"
                    }
                ],
            }
            ,{
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
    }];

    export default {
        data() {
            return {
                expandedKeys: ['0-0-0', '0-0-1'],
                autoExpandParent: true,
                checkedKeys: ['0-0-0'],
                selectedKeys: [],
                treeData,
                data,
                columns,

            };
        },
        watch: {
            checkedKeys(val) {
                console.log('onCheck', val);
            },
        },
        methods: {
            onExpand(expandedKeys) {
                console.log('onExpand', expandedKeys);
                // if not set autoExpandParent to false, if children expanded, parent can not collapse.
                // or, you can remove all expanded children keys.
                this.expandedKeys = expandedKeys;
                this.autoExpandParent = false;
            },
            onCheck(checkedKeys) {
                console.log('onCheck', checkedKeys);
                this.checkedKeys = checkedKeys;
            },
            onSelect(selectedKeys, info) {
                console.log('onSelect', info);
                this.selectedKeys = selectedKeys;
            },
        },
    };
</script>

<style scoped>

</style>