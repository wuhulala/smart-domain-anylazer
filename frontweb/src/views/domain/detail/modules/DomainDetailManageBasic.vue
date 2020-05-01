<style scoped>
    .avatar-uploader > .ant-upload {
        width: 128px;
        height: 128px;
    }

    .avatar-uploader  .ant-preview-img {
        width: 128px;
        height: 128px;
    }

    .ant-upload-select-picture-card i {
        font-size: 32px;
        color: #999999;
    }

    .ant-upload-select-picture-card .ant-upload-text {
        margin-top: 8px;
        color: #666;
    }
</style>
<template>
    <a-form-model :model="form" :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-form-model-item label="头像">
            <a-upload
                    name="file"
                    listType="picture-card"
                    class="avatar-uploader"
                    :showUploadList="false"
                    action="/file/upload"
                    @change="handleChange">
                <img v-if="imgUrl" class="ant-preview-img" :src="imgUrl" alt="avatar"/>
                <div v-else>
                    <a-icon :type="loading ? 'loading' : 'plus'"/>
                    <div class="ant-upload-text">上传</div>
                </div>
            </a-upload>
        </a-form-model-item>
        <a-form-model-item label="名称">
            <a-input v-model="form.name"/>
        </a-form-model-item>
        <a-form-model-item label="Activity zone">
            <a-select v-model="form.region" placeholder="please select your zone">
                <a-select-option value="shanghai">
                    Zone one
                </a-select-option>
                <a-select-option value="beijing">
                    Zone two
                </a-select-option>
            </a-select>
        </a-form-model-item>
        <a-form-model-item label="">
            <a-date-picker
                    v-model="form.date1"
                    show-time
                    type="date"
                    placeholder="Pick a date"
                    style="width: 100%;"
            />
        </a-form-model-item>
        <a-form-model-item label="Instant delivery">
            <a-switch v-model="form.delivery"/>
        </a-form-model-item>
        <a-form-model-item label="Activity type">
            <a-checkbox-group v-model="form.type">
                <a-checkbox value="1" name="type">
                    Online
                </a-checkbox>
                <a-checkbox value="2" name="type">
                    Promotion
                </a-checkbox>
                <a-checkbox value="3" name="type">
                    Offline
                </a-checkbox>
            </a-checkbox-group>
        </a-form-model-item>
        <a-form-model-item label="Resources">
            <a-radio-group v-model="form.resource">
                <a-radio value="1">
                    Sponsor
                </a-radio>
                <a-radio value="2">
                    Venue
                </a-radio>
            </a-radio-group>
        </a-form-model-item>
        <a-form-model-item label="Activity form">
            <a-input v-model="form.desc" type="textarea"/>
        </a-form-model-item>
        <a-form-model-item :wrapper-col="{ span: 14, offset: 4 }">
            <a-button type="primary" @click="onSubmit">
                Create
            </a-button>
            <a-button style="margin-left: 10px;">
                Cancel
            </a-button>
        </a-form-model-item>
    </a-form-model>
</template>


<script>

    function getBase64(img, callback) {
        const reader = new FileReader();
        reader.addEventListener('load', () => callback(reader.result));
        reader.readAsDataURL(img);
    }

    export default {
        name: "DomainDetailManageBasic",
        data() {
            return {

                loading: false,
                imgUrl: '',

                /////////////

                top: 10,
                bottom: 10,
                domainDetail: {},

                ////// form /////////
                labelCol: {span: 4},
                wrapperCol: {span: 14},
                form: {
                    name: '',
                    region: undefined,
                    date1: undefined,
                    delivery: false,
                    type: [],
                    resource: '',
                    desc: '',
                },

            }
        },
        created() {

        },
        mounted() {
        },
        methods: {
            handleChange(info) {
                debugger
                if (info.file.status === 'uploading') {
                    this.loading = true;
                    return;
                }
                if (info.file.status === 'done') {
                    getBase64(info.file.originFileObj, imgUrl => {
                        this.imgUrl = imgUrl;
                        this.loading = false;
                    })
                }
            },
            onSubmit() {
                console.log('submit!', this.form);
            },
        }
    }
</script>

<style scoped>

</style>