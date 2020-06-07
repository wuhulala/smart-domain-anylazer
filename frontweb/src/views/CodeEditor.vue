<template>
    <div>
        <div  class="editor">
            <div class="editor-header">
                <h3 style="display: inline-block; margin-right:10px; font-weight: bolder">实施指标作业</h3>
                <a-select default-value="sql" size="small" style="width: 120px" v-model="editorConfig.lang">
                    <a-select-option value="sql">
                        SQL
                    </a-select-option>
                    <a-select-option value="python">
                        Python
                    </a-select-option>
                    <a-select-option value="java">
                        Java
                    </a-select-option>
                </a-select>
                <span style="padding-left: 10px">
                    <a-icon type="play-circle" title="执行" style="cursor:pointer; font-size:16px; color:#1890ff" />
                </span>
            </div>
            <div class="editor-body">
                <div id="editor" class="editor-code">
                    <MonacoEditor class="box" v-model="editorConfig.code" :language="editorConfig.lang"/>
                </div>
                <div class="editor-config">
                    <div class="config-item" @click="showDrawer">指标配置</div>
                    <div class="config-item">数据源</div>
                    <div class="config-item">目标源</div>
                    <div class="config-item">其它配置</div>
                </div>
            </div>
        </div>
        <a-drawer
                placement="right"
                :closable="true"
                :visible="visible"
                :destroyOnClose="true"
                :mask="false"
                :wrapStyle="drawlerWrapStyle"

                :after-visible-change="afterVisibleChange"
                @close="onClose"
        >
            <p>Some contents...</p>
            <p>Some contents...</p>
            <p>Some contents...</p>
        </a-drawer>
    </div>

</template>

<script>
    import MonacoEditor from 'vue-monaco'

    export default {
        name: "codeEditor",
        components: {
            MonacoEditor
        },
        data() {
            return {
                editorConfig: {
                    code: 'select * from user_info',
                    lang: 'sql'
                },
                visible: false,
                drawlerWrapStyle: {right: '20px', top: '114px', height: '800px'}
            }
        },
        methods: {
            afterVisibleChange(val) {
                console.log('visible', val);
                if (val === false) {
                    this.drawlerWrapStyle = {};
                }
            },
            showDrawer() {
                this.drawlerWrapStyle = {right: '20px', top: '114px', height: '800px'}
                this.visible = true;
            },
            onClose() {
                this.visible = false;
            },
            getEditorEle() {
                return document.getElementById('editor');
            }
        },
    }
</script>

<style type="text/scss" lang="scss" scoped>
    .editor {
        width: 100%;
        height: 100%;

        .editor-header {
            height: 50px;
            border: 1px solid #ddd;
            padding: 10px 5px 10px 5px;
        }

        .editor-body {
            $sidebar-width: 25px;
            width: 100%;
            min-height: 800px;

            .editor-code {
                display: inline-block;
                width: calc(100% - #{$sidebar-width});
                min-height: 800px;

                .box {
                    width: 100%;
                    min-height: 800px;
                }
            }

            .editor-config {
                display: inline-block;
                width: $sidebar-width;
                float: right;
                min-height: 800px;
                background: #eee;
                z-index: 9999;
                border: 1px solid #eee;
                .config-item {
                    font-size: 12px;
                    margin: 1px 0;
                    cursor: pointer;
                    padding: 4px 8px;
                    background-color: #eeeeee; /* 不支持线性的时候显示 */
                    background-image: linear-gradient(to right, #ffffff, #eeeeee);
                }
            }
        }

        .ant-drawer-content-wrapper {
            background-color: #eeeeee; /* 不支持线性的时候显示 */
            box-shadow: none !important;
        }
    }
</style>