import https from '../../https'

export default {
    addGroup: function (group, callback) {
        let params = {
            query: group
        };
        https.fetchPost('/domain/group/add.json', params).then((result) => {
            callback(result.data);
        }).catch(err => {
            console.log(err)
        })
    },
    updateGroup: function (group, callback) {
        let params = {
            query: group
        };
        https.fetchPost('/domain/group/update.json', params).then((result) => {
            callback(result.data);
        }).catch(err => {
            console.log(err)
        })
    },
    deleteGroup: function (group, callback) {
        let params = {
            query: group
        };
        https.fetchPost('/domain/group/delete.json', params).then((result) => {
            callback(result.data);
        }).catch(err => {
            console.log(err)
        })
    },
    findGroupByParentId: function (parentId, callback) {
        let params = {
            parentId: parentId
        };
        https.fetchPost('/domain/group/find_domain_group_by_parent.json', params).then((result) => {
            callback(result.data);
        }).catch(err => {
            console.log(err)
        })
    },

    //////////////////////////////////////////////////////////////////////////////////
    addDomain: function (group, callback) {
        let params = {
            query: group
        };
        https.fetchPost('/domain/add.json', params).then((result) => {
            callback(result.data);
        }).catch(err => {
            console.log(err)
        })
    },
    updateDomain: function (group, callback) {
        let params = {
            query: group
        };
        https.fetchPost('/domain/update.json', params).then((result) => {
            callback(result.data);
        }).catch(err => {
            console.log(err)
        })
    },
    deleteDomain: function (group, callback) {
        let params = {
            query: group
        };
        https.fetchPost('/domain/delete.json', params).then((result) => {
            callback(result.data);
        }).catch(err => {
            console.log(err)
        })
    },
    findDomainByGroupId: function (groupId, callback) {
        let params = {
            groupId: groupId
        };
        https.fetchPost('/domain/find_domain_by_group.json', params).then((result) => {
            callback(result.data);
        }).catch(err => {
            console.log(err)
        })
    },
    findLeast5Domain: function (callback) {
        let params = {
        };
        https.fetchPost('/domain/least5.json', params).then((result) => {
            callback(result.data);
        }).catch(err => {
            console.log(err)
        })
    },
    findReview5Domain: function (callback) {
        let params = {
        };
        https.fetchPost('/domain/review5.json', params).then((result) => {
            callback(result.data);
        }).catch(err => {
            console.log(err)
        })
    }
}