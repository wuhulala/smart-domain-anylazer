<template>
    <div>
        <a-row :gutter="[40,48]">
            <a-col :span="4">
                <a-row>
                    <a-col :span="16" >
                        <span>当前选中:
                            <a-tag @close="resetCurGroup">{{curGroup.groupName}}</a-tag>
                            <a-icon type="close"  @click="resetCurGroup" />
                        </span>
                    </a-col>
                    <a-col :span="8">
                        <a-button icon="plus" style="float: right" size="small" @click="showAddGroupModal"></a-button>
                    </a-col>
                </a-row>
                <hr/>
                <a-input-search style="margin-bottom: 8px" placeholder="Search" @change="filterGroup" />
                <a-tree :loadData="onLoadData"
                        @select="switchGroup"
                        @expand="onExpand"
                        :expandedKeys="expandedKeys"
                        :autoExpandParent="autoExpandParent"
                        :treeData="groupData"/>
            </a-col>
            <a-col :span="20">
                <a-form-model layout="inline" :model="addForm" ref="addForm"
                              @submit.native.prevent>
                    <a-form-model-item :wrapper-col="{span: 24}"
                                       :rules="{
                                                 required: true,
                                                 message: 'domain can not be null',
                                                 trigger: 'blur',
                                               }">
                        <a-input v-model="addForm.name" placeholder="快来添加一个新的领域吧....">
                        </a-input>
                    </a-form-model-item>
                    <a-form-model-item>
                        <a-button type="primary" icon="plus" html-type="submit" @click="submitAddForm('addForm')">
                            添加领域
                        </a-button>
                    </a-form-model-item>
                </a-form-model>

                <a-table :columns="columns" :dataSource="domainData">
                    <span slot="name" slot-scope="name, record">
                       <router-link target="_blank" :to="{name: 'DomainDetailManage', params: {id: record.id}}" >{{name}}</router-link>
                    </span>
                    <span slot="tags" slot-scope="tags">
                        <a-tag
                                v-for="tag in tags"
                                :color="tag==='loser' ? 'volcano' : (tag.length > 5 ? 'geekblue' : 'green')"
                                :key="tag">
                            {{tag.toUpperCase()}}
                        </a-tag>
                    </span>
                    <span slot="action">
                        <a href="javascript:;">更新</a>
                        <a-divider type="vertical"/>
                        <a href="javascript:;">删除</a>
                    </span>
                </a-table>
            </a-col>
        </a-row>

        <domain-group-add :show="showAddGroup"
                          :after-close="afterAddGroupModalClose"
                          :after-save="afterAddGroupSuccess"
                          :parent-group="curGroup">
        </domain-group-add>

    </div>
</template>


<script>

    import DomainGroupAdd from "./DomainGroupAdd";
    import DeepClone from "../../../util/DeepClone";
    import DomainService from "../../../api/domain/DomainService";

    const columns = [
        {
            title: '名称',
            dataIndex: 'name',
            slots: {title: 'customTitle'},
            scopedSlots: {customRender: 'name'},
        },
        {
            title: '标签',
            dataIndex: 'tags',
            scopedSlots: {customRender: 'tags'},
        },
        {
            title: '创建人',
            slots: {title: 'customTitle'},
            dataIndex: 'creator',
        },
        {
            title: "创建时间",
            dataIndex: 'gmtCreate',
            slots: {title: 'customTitle'},
        },
        {
            title: '操作',
            scopedSlots: {customRender: 'action'},
        }
    ];

    const dataList = [];
    const generateList = data => {
        for (let i = 0; i < data.length; i++) {
            const node = data[i];
            const key = node.key;
            dataList.push({ key, title: node.title });
            if (node.children) {
                generateList(node.children);
            }
        }
    };

    const getParentKey = (key, tree) => {
        let parentKey;
        for (let i = 0; i < tree.length; i++) {
            const node = tree[i];
            if (node.children) {
                if (node.children.some(item => item.key === key)) {
                    parentKey = node.key;
                } else if (getParentKey(key, node.children)) {
                    parentKey = getParentKey(key, node.children);
                }
            }
        }
        debugger
        return parentKey;
    };
    export default {
        components: {DomainGroupAdd},
        data() {
            return {

                /////// domain start //////////
                columns: columns,
                domainData: [],

                ////// add form ////////
                addForm: {},


                ///////////// group ///////////////
                expandedKeys: [],
                searchValue: '',
                autoExpandParent: true,
                checkedKeys: ['0-0-0'],
                selectedKeys: [],

                groupData: [],
                curGroupNode: null,
                curGroup: {
                    id: "-1",
                    groupName: "默认"
                },


                /////// add group form ///////
                showAddGroup: false,




            };
        },
        watch: {
            groupData: function (newData) {
                generateList(newData);
            }
        },
        created: function () {
            // `this` 指向 vm 实例
            this.initGroupList();
            this.changeDomainList("-1");
        },
        methods: {
            onExpand(expandedKeys) {
                this.expandedKeys = expandedKeys;
                this.autoExpandParent = false;
            },
            resetCurGroup() {
                this.curGroup = {
                    id: "-1",
                    groupName: "默认"
                }
                this.curGroupNode = null;
            },
            filterGroup(e) {
                debugger
                const value = e.target.value;
                const expandedKeys = dataList
                    .map(item => {
                        debugger
                        if (item.title.indexOf(value) > -1) {
                            return item.key;
                        }
                        return null;
                    })
                    // .filter((item, i, self) => item && self.indexOf(item) === i);

                Object.assign(this, {
                    expandedKeys,
                    searchValue: value,
                    autoExpandParent: true,
                });
            },
            onLoadData(treeNode) {
                return new Promise(resolve => {
                    // if (treeNode.dataRef.children) {
                    //     resolve();
                    //     return;
                    // }
                    DomainService.findGroupByParentId(treeNode.dataRef.key,  (result) => {
                        treeNode.dataRef.children = Array.from(result.list, (group) => {
                            return {
                                title: group.groupName,
                                key: group.id,
                                _meta: group
                            }
                        });
                        this.groupData = [...this.groupData];
                        resolve();
                    });

                });
            },
            showAddGroupModal() {
                this.showAddGroup = true;
            },
            afterAddGroupModalClose() {
                this.showAddGroup = false;
            },
            afterAddGroupSuccess(group) {
                if (this.curGroupNode) {
                    // if (!this.curGroupNode.children) {
                    //     this.curGroupNode.children = [];
                    // }
                    // this.curGroupNode.children.push(this.transGroup(group))
                    this.onLoadData(this.curGroupNode);
                } else {
                    this.groupData.push(this.transGroup(group));
                }
                this.groupData = [...this.groupData];
            },
            switchGroup(group, e) {
                this.curGroup = e.node.dataRef._meta;
                this.curGroupNode = e.node;
                this.changeDomainList(group[0]);
            },

            initGroupList() {
                DomainService.findGroupByParentId("-1", (result) => {
                    this.groupData = Array.from(result.list, (group) => {
                        return {
                            title: group.groupName,
                            key: group.id,
                            _meta: group
                        }
                    });
                })
            },
            transGroup(group) {
                return {
                    title: group.groupName,
                    key: group.id,
                    _meta: group
                }
            },


            submitAddForm(formName) {
                this.$refs[formName].validate(valid => {
                    if (valid) {
                        let domain = DeepClone.clone(this.addForm);
                        if (this.curGroup && this.curGroup.id) {
                            domain.groupId = this.curGroup.id;
                        } else {
                            domain.groupId = "-1";
                        }
                        DomainService.addDomain(domain, (result) => {
                            this.refreshDomainList()
                            this.$message.info('新增成功');
                            this.addForm = {};
                        })
                    } else {
                        this.$message.warn('新增失败');
                        return false;
                    }
                });
            },
            refreshDomainList() {
                this.changeDomainList(this.curGroup.id || "-1")
            },
            changeDomainList(groupId) {
                let that = this;
                DomainService.findDomainByGroupId(groupId, function (result) {
                    that.domainData = [...result.list];
                })
            },
            goDetail(id) {
                this.$router.push({name: 'DomainManageDetail', params: {id : id}})
            }
        },
    };
</script>

<style scoped>

</style>