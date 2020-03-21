<template>
    <div>
        <a-modal title="来添加一个新的分组..." :width="760" v-model="visible" :after-close="afterClose" @cancel="resetForm"
                 @ok="onSubmit">
            <a-form-model
                    ref="ruleForm"
                    :model="form"
                    :rules="rules"
                    :label-col="labelCol"
                    :wrapper-col="wrapperCol"
            >
                <a-form-model-item label="父分组" prop="parentName" ref="parentName">
                    <a-input v-model="parentGroup.groupName" disabled="true"/>
                </a-form-model-item>
                <a-form-model-item label="分组" prop="name" ref="name">
                    <a-input v-model="form.groupName" @blur="() => {$refs.name.onFieldBlur()}"/>
                </a-form-model-item>

            </a-form-model>
        </a-modal>
    </div>
</template>


<script>

    import DomainService from "../../../api/domain/DomainService";
    import DeepClone from "../../../util/DeepClone";

    export default {
        props: {
            show: {
                type: Boolean,
                default: function () {
                    return false
                }
            },
            afterClose: Function,
            parentGroup: {
                type: Object,
                default: function () {
                    return {
                        id: "-1",
                        groupName: "顶级"
                    }
                }
            },
            afterSave: {
                type: Function,
                default: function () {

                }
            }
        },
        data() {
            return {
                visible: false,
                labelCol: {span: 4},
                wrapperCol: {span: 14},
                other: '',
                form: {},
                rules: {
                    groupName: [
                        {required: true, message: 'Please input groupName', trigger: 'blur'},
                    ],
                },
            };
        },
        watch: {
            show: function (newState, oldState) {
                this.visible = newState;
            }
        },
        methods: {
            onSubmit() {
                let that = this;
                this.$refs.ruleForm.validate(valid => {
                    if (valid) {
                        let group = DeepClone.clone(this.form);
                        group.parentId = this.parentGroup.id;
                        DomainService.addGroup(group, (result) => {
                            this.visible = false;
                            this.resetForm();
                            this.$message.info("新增分组成功");
                            this.afterSave(result.item);
                        })
                    } else {
                        console.log('error submit!!');
                    }
                });
            },
            resetForm() {
                this.form = {};
                this.$refs.ruleForm.resetFields();
            },
        },
    };
</script>

<style scoped>

</style>