// 角色数据管理
let roles=[]

const RoleData = {

    permissions: ['超级管理员', '管理员', '普通用户'],

    async updateRole(id, updates) {
        const response = await fetch(`${SERVER_URL}/update_role`, {
            method: 'POST',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json',
            },
            body:JSON.stringify({
                id: id,
                role: updates.permiss
            }),
        });
        const data = await response.json();
        if (data.success) {
            const role = this.getRoleById(id);
            Object.assign(role, updates);
            return true;
        }
        return false;
    },

    getRoleById(id) {
        return roles.find(r => r.id === id);
    },

    searchRoles(term) {
        if (!term) return roles;
        return roles.filter(role =>
            role.id.toString().includes(term)
            || role.name.includes(term)
        );
    }
};

// 角色管理逻辑
const roleManager = {
    currentPage: 1,
    pageSize: 10,
    currentEditId: null,
    currentPermissionId: null,

    init() {
        this.renderTable();
        this.setupEventListeners();
    },

    setupEventListeners() {
        document.getElementById('searchInput').addEventListener('input', (e) => {
            this.handleSearch();
        });
    },

    handleSearch() {
        const searchTerm = document.getElementById('searchInput').value;
        const filteredRoles = RoleData.searchRoles(searchTerm);
        this.renderTable(filteredRoles);
    },

    renderTable(roles1 = roles) {
        const startIndex = (this.currentPage - 1) * this.pageSize;
        const pageRoles = roles1.slice(startIndex, startIndex + this.pageSize);

        const tableBody = document.getElementById('tableBody');
        tableBody.innerHTML = '';

        pageRoles.forEach((role, index) => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${role.id}</td>
                <td>${role.name}</td>
                <td><span class="tag ${role.permiss >0 ? 'tag-success' : 'tag-danger'}">
                    ${role.permiss >0 ? '启用' : '禁用'}
                </span></td>
                <td>${getRoleName(role.permiss)}</td>
                <td>
                    <div class="button-container">
                    <button onclick="checkAndExecute('handlePermission',${role.id})">权限</button>
                    <button onclick="checkAndExecute('handleEdit',${role.id})">编辑</button>
                    </div>
                </td>
            `;
            tableBody.appendChild(row);
        });

        this.updatePagination(roles1.length);

    },

    updatePagination(totalItems) {
        const totalPages = Math.ceil(totalItems / this.pageSize);
        document.getElementById('currentPage').textContent = this.currentPage;
        document.getElementById('totalPages').textContent = totalPages;
    },

    prevPage() {
        if (this.currentPage > 1) {
            this.currentPage--;
            this.renderTable();
        }
    },

    nextPage() {
        const totalPages = Math.ceil(roles.length / this.pageSize);
        if (this.currentPage < totalPages) {
            this.currentPage++;
            this.renderTable();
        }
    },

    handleEdit(id) {
        const role = RoleData.getRoleById(id);
        if (role) {
            this.currentEditId = id;
            this.currentPermissionId = role.permiss;
            document.getElementById('dialogTitle').textContent = '编辑角色';
            document.getElementById('roleName').value = role.name;
            document.getElementById('roleStatus').value = role.status.toString();
            document.getElementById('roleDialog').style.display = 'block';
        }
    },

    async handleSubmit(event) {
        event.preventDefault();

        const roleStatus = document.getElementById('roleStatus').value === 'true';
        this.currentPermissionId=parseInt(this.currentPermissionId);
        this.currentPermissionId = Math.abs(this.currentPermissionId);
        if (roleStatus==false){
            this.currentPermissionId= -this.currentPermissionId;
        }
        const response = await fetch(`${SERVER_URL}/update_role`, {
            method: 'POST',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                id: this.currentEditId,
                role: this.currentPermissionId
            }),
        });
        const data = await response.json();
        if (data.success) {
            this.closeDialog();
            location.reload();
        }

    },

    closeDialog() {
        document.getElementById('roleDialog').style.display = 'none';
    },

    handlePermission(id) {
        const role = RoleData.getRoleById(id);
        if (role) {
            this.currentEditId = id;

            const permissionContent = document.getElementById('permissionContent');
            permissionContent.innerHTML = `
                <p><strong>角色:</strong> ${role.name}</p>
                <p><strong>当前权限等级:</strong> ${role.permiss}</p>
            `;

            const permissionRadios = document.getElementById('permissionRadios');
            permissionRadios.innerHTML = RoleData.permissions.map(permission => `
                <label>
                    <input type="radio" name="permission" value="${permission}"
                        ${getRoleName(role.permiss) === permission ? 'checked' : ''}>
                    ${permission}
                </label>
            `).join('');

            document.getElementById('permissionDialog').style.display = 'block';
        }
    },

    savePermissions() {
        const selectedPermission = document.querySelector('input[name="permission"]:checked');
        if (selectedPermission && this.currentEditId) {
            RoleData.updateRole(this.currentEditId, {
                permiss: getRole(selectedPermission.value)
            });
            this.closePermissionDialog();
            this.renderTable();
            location.reload();
        }
    },

    closePermissionDialog() {
        document.getElementById('permissionDialog').style.display = 'none';
    }
};


document.addEventListener('DOMContentLoaded', async () => {
    const data = await fetchAPI('/users');
    roles = data.users.map(user => ({
        id: user.id,
        name: user.username,
        status: user.status < 0 ? false : true,
        permiss: user.role
    }));

    roleManager.init();


    // 添加按钮悬停效果
    const buttons = document.querySelectorAll('button');
    buttons.forEach(button => {
        button.addEventListener('mouseenter', () => {
            button.style.transform = 'translateY(-1px)';
        });
        button.addEventListener('mouseleave', () => {
            button.style.transform = 'translateY(0)';
        });
    });

    // 添加表格行悬停效果
    const tableBody = document.getElementById('tableBody');
    tableBody.addEventListener('mouseover', (e) => {
        const tr = e.target.closest('tr');
        if (tr) {
            tr.style.backgroundColor = '#f5f5f5';
        }
    });
    tableBody.addEventListener('mouseout', (e) => {
        const tr = e.target.closest('tr');
        if (tr) {
            tr.style.backgroundColor = '';
        }
    });

    // 添加对话框动画
    const dialogs = document.querySelectorAll('.dialog');
    dialogs.forEach(dialog => {
        dialog.addEventListener('click', (e) => {
            if (e.target === dialog) {
                dialog.style.display = 'none';
            }
        });
    });

});

function checkAndExecute (methodName,roleid) {
    try {
        const user=JSON.parse(sessionStorage.getItem('user'));
        const role = parseInt(user.role);
        if (role < 0) {
            alert('您的权限被禁用，请联系超级管理员');
            return;
        }
        if (role == 1) {
            roleManager[methodName](roleid);
        } else {
            alert('权限不足，只有超级管理员可以删除用户');
        }
    }
    catch (error) {
        console.error('检查用户权限失败:', error);
        alert('权限检查失败，请重试');
    }

}

function getRoleName(role) {
        if(role<0)role=-role;
        const roleNames = {
            '1': '超级管理员',
            '2': '管理员',
            '3': '普通用户'
        };
        return roleNames[role] || '未知';
    }
function getRole(role) {
    const roleNames = {
        '超级管理员':1,
        '管理员':2,
        '普通用户':3
    };
    return roleNames[role] || '未知';
}