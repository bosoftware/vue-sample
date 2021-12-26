<template>
    <div>
        <a-row>
            <a-form-model ref="searchForm" :model="form" :rules="rules" layout="inline" @submit="handleSubmit" @submit.native.prevent>
                <a-form-model-item ref="surname" label="Surname" prop="surname" labelAlign="left">
                    <a-input
                        v-model="form.surname"
                        @blur="
                            () => {
                                $refs.surname.onFieldBlur();
                            }
                        "
                    />
                </a-form-model-item>
                <a-form-model-item ref="givenname" label="Given Name" prop="givenname" labelAlign="left">
                    <a-input
                        v-model="form.givenname"
                        @blur="
                            () => {
                                $refs.givenname.onFieldBlur();
                            }
                        "
                    />
                </a-form-model-item>
                <a-form-model-item ref="gender" label="Gender" prop="gender" labelAlign="left">
                    <a-select placeholder="Select Gender" style="width: 120px" v-model="form.gender">
                        <a-select-option value="Male"> Male </a-select-option>
                        <a-select-option value="Female"> Female </a-select-option>
                    </a-select>
                </a-form-model-item>
                <a-form-model-item ref="address" label="Address" prop="address" labelAlign="left">
                    <a-input
                        v-model="form.address"
                        @blur="
                            () => {
                                $refs.givenname.onFieldBlur();
                            }
                        "
                    />
                </a-form-model-item>
                <a-form-model-item :wrapper-col="{ span: 12, offset: 5 }">
                    <a-button type="primary" html-type="submit"> Submit </a-button>
                </a-form-model-item>
            </a-form-model>
        </a-row>
        <div>
            <a-row>
                <a-table
                    :row-key="(record) => record.uuid"
                    :data-source="data"
                    :pagination="pagination"
                    :loading="loading"
                    :columns="columns"
                    @change="handleTableChange"
                >
                </a-table>
            </a-row>
        </div>
    </div>
</template>

<script>
import { usersearch } from '@/api/index';
const data = [];
const columns = [
    {
        title: 'uuid',

        dataIndex: 'uuid',
        key: 'uuid',
        fixed: 'left'
    },
    { title: 'Surname', dataIndex: 'surname', key: 'surname' },

    { title: 'Given Name', dataIndex: 'givenname', key: 'givenname' },
    { title: 'Email Address', dataIndex: 'email', key: 'email' },
    { title: 'Address', dataIndex: 'address', key: 'address' }
];
export default {
    components: {},
    data() {
        return {
            form: {
                surname: '',
                givenname: '',
                gender: '',
                address: ''
            },
            rules: {
                surname: [{ required: true, message: 'Please input surname', trigger: 'blur' }],
                givenname: [{ required: true, message: 'Please input given name', trigger: 'blur' }]
            },
            loading: false,
            data,
            columns,
            pagination: { current: 0, pageSize: 20 }
        };
    },
    methods: {
        handleSubmit(e) {
            this.$refs.searchForm.validate((valid) => {
                if (valid) {
                    this.searchUser(this.form);
                } else {
                    this.$message.error('Please check your input.');
                    return false;
                }
            });
        },
        searchUser(query) {
            usersearch(query, this.pagination.current - 1, this.pagination.pageSize)
                .then((res) => {
                    this.$message.success('Search successed');
                    const pagination = { ...this.pagination };
                    pagination.total = res.totalElements;
                    this.loading = false;
                    this.data = res.content;
                    this.pagination = pagination;
                })
                .catch((err) => {
                    this.loading = false;
                    this.$message.error('Search error.');
                });
        },
        handleTableChange(pagination, filters, sorter) {
            const pager = { ...this.pagination };
            pager.current = pagination.current;
            this.pagination = pager;
            this.searchUser(this.form);
        }
    }
};
</script>
<style lang='less' scoped>
@background_color: #0f99a2;
.btn_group {
    margin-bottom: 16px;
    button {
        margin-right: 12px;
    }
}
.search {
    display: flex;
    flex-direction: row-reverse;
}
/deep/ .ant-table-body {
    overflow-x: auto;
}
.tag {
    span {
        width: 52px;
        text-align: center;
        line-height: 26px;
        color: #fff;
        background: @background_color;
    }
    .invalid {
        color: @background_color;
        background: none;
        border: 1px solid @background_color;
    }
}
</style>