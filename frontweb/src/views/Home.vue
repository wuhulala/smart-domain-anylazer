<style scoped>
    /* For demo */
    .ant-carousel >>> .slick-slide {
        text-align: center;
        height: 160px;
        line-height: 160px;
        background: #364d79;
        overflow: hidden;
    }

    .ant-carousel >>> .custom-slick-arrow {
        width: 25px;
        height: 25px;
        font-size: 25px;
        color: #fff;
        background-color: rgba(31, 45, 61, 0.11);
        opacity: 0.3;
    }

    .ant-carousel >>> .custom-slick-arrow:before {
        display: none;
    }

    .ant-carousel >>> .custom-slick-arrow:hover {
        opacity: 0.5;
    }

    .ant-carousel >>> .slick-slide h3 {
        color: #fff;
    }

    .home-content {
        margin-top: 20px;
        padding: 10px 20px;
    }



    .home-content h3 {
        border-left: 3px #52a1de solid;
        padding-left: 10px;
        font-weight: bolder;
    }

    .rank-content-box {
        margin-top: 20px;
    }

    .ant-col {
        padding: 15px;
    }

</style>
<template>
    <div>
        <a-carousel arrows>
            <div
                    slot="prevArrow"
                    slot-scope="props"
                    class="custom-slick-arrow"
                    style="left: 10px;zIndex: 1"
            >
                <a-icon type="left-circle"/>
            </div>
            <div slot="nextArrow" slot-scope="props" class="custom-slick-arrow" style="right: 10px">
                <a-icon type="right-circle"/>
            </div>
            <div><h3>领域梳理</h3></div>
            <div><h3>智能分析</h3></div>
            <div><h3>炫酷可视化</h3></div>
            <div><h3>知识串联</h3></div>
        </a-carousel>

        <a-row  class="home-content">
            <a-col :span="16">
                <h3>领域总览</h3>
                <div
                        class="demo-infinite-container"
                        v-infinite-scroll="handleInfiniteOnLoad"
                        :infinite-scroll-disabled="busy"
                        :infinite-scroll-distance="10"
                >
                    <domain-overview></domain-overview>
                </div>
            </a-col>
            <a-col :span="8" class="rank-content">
                <div class="rank-content-box">
                    <h3>最新</h3>
                    <a-list bordered :dataSource="least5Data">
                        <a-list-item slot="renderItem" slot-scope="item, index">
                            <a-list-item-meta
                                    :description="item.creator"
                            >
                                <router-link slot="title" target="_blank" :to="{ name: 'DomainDetail', params: { id: item.id }}">
                                    {{item.name}}
                                </router-link>
                                <a-avatar shape="square" slot="avatar" size="large" :style="{background: randomColor(), verticalAlign: 'middle'}">
                                    {{item.name}}
                                </a-avatar>
                            </a-list-item-meta>
                        </a-list-item>
                    </a-list>
                </div>
                <div class="rank-content-box">
                    <h3>待复习</h3>
                    <a-list bordered :dataSource="reviewData">
                        <a-list-item slot="renderItem" slot-scope="item, index">
                            <a-list-item-meta
                                    :description="item.creator"
                            >
                                <router-link slot="title" target="_blank" :to="{ name: 'DomainDetail', params: { id: item.id }}">
                                    {{item.name}}
                                </router-link>
                                <a-avatar shape="square" slot="avatar" size="large" :style="{background: randomColor(), verticalAlign: 'middle'}">
                                    {{item.name}}
                                </a-avatar>
                            </a-list-item-meta>
                        </a-list-item>
                    </a-list>
                </div>
            </a-col>
        </a-row>

    </div>
</template>


<script>

    import reqwest from 'reqwest';
    import infiniteScroll from 'vue-infinite-scroll';
    import DomainOverview from "./domain/DomainOverview";
    import DomainService from "../api/domain/DomainService";
    import Domain from "./Domain";
    const fakeDataUrl = 'https://randomuser.me/api/?results=5&inc=name,gender,email,nat&noinfo';

    export default {
        name: "Home",
        components: {DomainOverview},
        directives: { infiniteScroll },
        data() {
            return {
                data:   [
                    'Racing car sprays burning fuel into crowd.',
                    'Japanese princess to wed commoner.',
                    'Australian walks 100km after outback crash.',
                    'Man charged over missing wedding girl.',
                    'Los Angeles battles huge wildfires.',
                ],
                reviewData: [
                    'Racing car sprays burning fuel into crowd.',
                    'Japanese princess to wed commoner.',
                    'Australian walks 100km after outback crash.',
                    'Man charged over missing wedding girl.',
                    'Los Angeles battles huge wildfires.',
                ],
                least5Data:[],
                data2: [],
                loading: false,
                busy: false,
            }
        },
        beforeMount() {
            this.fetchData(res => {
                this.data2 = res.results;
            });
            this.initLeast5Domain();
            this.initReview5Domain();
        },
        mounted() {

        },
        methods: {
            fetchData(callback) {
                reqwest({
                    url: fakeDataUrl,
                    type: 'json',
                    method: 'get',
                    contentType: 'application/json',
                    success: res => {
                        callback(res);
                    },
                });
            },
            handleInfiniteOnLoad() {
                // TODO 加载 domaingroup + domainpreview
            },
            initLeast5Domain() {
                DomainService.findLeast5Domain((result) => {
                    this.least5Data = result.list;
                })
            },
            initReview5Domain() {
                DomainService.findReview5Domain((result) => {
                    this.reviewData = result.list;
                })
            },
            randomColor() {
                let r = parseInt(Math.random() * 256)
                let g = parseInt(Math.random() * 256)
                let b = parseInt(Math.random() * 256)

                return `rgba(${r},${g},${b},0.8)`
            }
        }
    }
</script>

<style scoped>

</style>