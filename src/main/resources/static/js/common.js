/* 医院废料管理系统 - 公共JavaScript */

const App = {
    // API基础URL
    baseUrl: '/api/v1',

    // 初始化
    init: function() {
        this.bindEvents();
    },

    // 绑定事件
    bindEvents: function() {
        // 关闭弹窗
        $(document).on('click', '.modal-close, .modal .btn-secondary', function() {
            App.closeModal($(this).closest('.modal'));
        });

        // 点击遮罩关闭弹窗
        $(document).on('click', '.modal', function(e) {
            if (e.target === this) {
                App.closeModal($(this));
            }
        });

        // ESC键关闭弹窗
        $(document).keydown(function(e) {
            if (e.key === 'Escape') {
                $('.modal.show').each(function() {
                    App.closeModal($(this));
                });
            }
        });
    },

    // 显示消息
    showMessage: function(message, type) {
        const alertClass = type === 'success' ? 'alert-success' :
                          type === 'error' ? 'alert-error' :
                          type === 'warning' ? 'alert-warning' : 'alert-info';

        const alert = $(`<div class="alert ${alertClass}">${message}</div>`);
        $('.content').prepend(alert);

        setTimeout(function() {
            alert.fadeOut(function() {
                $(this).remove();
            });
        }, 3000);
    },

    // 显示加载
    showLoading: function() {
        const loading = $('<div class="loading"></div>');
        $('body').append(loading);
        return loading;
    },

    // 隐藏加载
    hideLoading: function(loading) {
        if (loading) {
            loading.remove();
        }
    },

    // 打开弹窗
    openModal: function(modalId) {
        $('#' + modalId).addClass('show');
    },

    // 关闭弹窗
    closeModal: function(modal) {
        if (typeof modal === 'string') {
            modal = $('#' + modal);
        }
        modal.removeClass('show');
    },

    // API请求
    request: function(url, options) {
        const loading = this.showLoading();

        // 获取token
        const token = localStorage.getItem('token');
        const userId = localStorage.getItem('userId');

        return fetch(url, {
            headers: {
                'Content-Type': 'application/json',
                'X-Token': token || '',
                'X-User-Id': userId || '',
                ...options.headers
            },
            ...options
        })
        .then(response => response.json())
        .then(data => {
            this.hideLoading(loading);
            return data;
        })
        .catch(error => {
            this.hideLoading(loading);
            this.showMessage('请求失败: ' + error.message, 'error');
            return { code: 500, message: error.message };
        });
    },

    // GET请求
    get: function(url, params) {
        let queryString = '';
        if (params) {
            queryString = '?' + new URLSearchParams(params).toString();
        }
        return this.request(url + queryString, { method: 'GET' });
    },

    // POST请求
    post: function(url, data) {
        return this.request(url, {
            method: 'POST',
            body: JSON.stringify(data)
        });
    },

    // PUT请求
    put: function(url, data) {
        return this.request(url, {
            method: 'PUT',
            body: JSON.stringify(data)
        });
    },

    // DELETE请求
    delete: function(url) {
        return this.request(url, { method: 'DELETE' });
    },

    // 确认对话框
    confirm: function(message) {
        return confirm(message);
    },

    // 格式化日期
    formatDate: function(dateString) {
        if (!dateString) return '-';
        const date = new Date(dateString);
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        const hours = String(date.getHours()).padStart(2, '0');
        const minutes = String(date.getMinutes()).padStart(2, '0');
        return `${year}-${month}-${day} ${hours}:${minutes}`;
    },

    // 格式化日期（仅日期）
    formatDateOnly: function(dateString) {
        if (!dateString) return '-';
        const date = new Date(dateString);
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        return `${year}-${month}-${day}`;
    },

    // 获取状态文本
    getStatusText: function(status, type) {
        const statusMap = {
            'department': { 1: '正常', 0: '停用' },
            'waste-category': { 1: '正常', 0: '停用' },
            'warehouse': { 1: '正常', 0: '停用' },
            'disposal-org': { 1: '正常', 0: '停用' },
            'waste': { 1: '待收集', 2: '已收集', 3: '已转运', 4: '已处置' },
            'transfer': { 1: '待转运', 2: '运输中', 3: '已到达', 4: '已完成' },
            'storage': { 1: '贮存中', 2: '已出库' },
            'disposal': { 1: '待处置', 2: '已处置' }
        };

        if (type && statusMap[type]) {
            return statusMap[type][status] || '未知';
        }
        return status;
    },

    // 生成UUID
    generateUUID: function() {
        return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
            const r = Math.random() * 16 | 0;
            const v = c === 'x' ? r : (r & 0x3 | 0x8);
            return v.toString(16);
        });
    }
};

// 页面管理类
const PageManager = {
    currentPage: 1,
    pageSize: 10,
    total: 0,

    // 初始化分页
    initPagination: function(options) {
        this.currentPage = options.currentPage || 1;
        this.pageSize = options.pageSize || 10;
        this.total = options.total || 0;
        this.onPageChange = options.onPageChange || function() {};

        this.render();
    },

    // 渲染分页
    render: function() {
        const totalPages = Math.ceil(this.total / this.pageSize);
        let html = '';

        // 上一页
        html += `<span class="page-item ${this.currentPage === 1 ? 'disabled' : ''}" data-page="${this.currentPage - 1}">上一页</span>`;

        // 页码
        for (let i = 1; i <= totalPages; i++) {
            if (i === 1 || i === totalPages || (i >= this.currentPage - 2 && i <= this.currentPage + 2)) {
                html += `<span class="page-item ${i === this.currentPage ? 'active' : ''}" data-page="${i}">${i}</span>`;
            } else if (i === this.currentPage - 3 || i === this.currentPage + 3) {
                html += `<span class="page-item disabled">...</span>`;
            }
        }

        // 下一页
        html += `<span class="page-item ${this.currentPage === totalPages ? 'disabled' : ''}" data-page="${this.currentPage + 1}">下一页</span>`;

        // 总计
        html += `<span class="page-item disabled">共 ${this.total} 条</span>`;

        $('.pagination').html(html);
    },

    // 绑定点击事件
    bindClick: function() {
        const self = this;
        $(document).on('click', '.pagination .page-item:not(.disabled):not(.active)', function() {
            const page = $(this).data('page');
            if (page && page !== self.currentPage) {
                self.currentPage = page;
                self.onPageChange(page);
                self.render();
            }
        });
    }
};

// 表单管理类
const FormManager = {
    // 获取表单数据
    getData: function(formId) {
        const data = {};
        $('#' + formId).find('input, select, textarea').each(function() {
            const name = $(this).attr('name');
            const value = $(this).val();
            if (name) {
                data[name] = value;
            }
        });
        return data;
    },

    // 设置表单数据
    setData: function(formId, data) {
        $('#' + formId).find('input, select, textarea').each(function() {
            const name = $(this).attr('name');
            if (data[name] !== undefined) {
                $(this).val(data[name]);
            }
        });
    },

    // 清空表单
    clear: function(formId) {
        $('#' + formId)[0].reset();
    },

    // 验证表单
    validate: function(formId) {
        let valid = true;
        $('#' + formId).find('[required]').each(function() {
            if (!$(this).val()) {
                $(this).addClass('is-invalid');
                valid = false;
            } else {
                $(this).removeClass('is-invalid');
            }
        });
        return valid;
    }
};

// 页面加载完成后初始化
$(document).ready(function() {
    App.init();
    PageManager.bindClick();
});
