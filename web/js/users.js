document.addEventListener('DOMContentLoaded', () => {
    // 初始化数据
    fetchUserData();

    // 为搜索框添加实时搜索功能
    document.getElementById('searchInput').addEventListener('input', handleSearch);
});

let allUsers = [];
let currentPage = 1;
const pageSize = 10;

async function fetchUserData() {
    try {
        const data = await fetchAPI('/users');
        if (data.success) {
            allUsers = data.users;
            renderTable(allUsers);
        }
    } catch (error) {
        console.error('获取用户失败:', error);
    }
}

function paginateUsers(users, page, pageSize) {
    const start = (page - 1) * pageSize;
    const end = start + pageSize;
    return users.slice(start, end);
}

function renderTable(users) {
    const tableBody = document.getElementById('tableBody');
    tableBody.innerHTML = '';

    const paginatedUsers = paginateUsers(users, currentPage, pageSize);

    paginatedUsers.forEach(user => {
        const row = `
            <tr>
                <td>${user.id}</td>
                <td>${user.username}</td>
                <td>${user.tel}</td>
                <td>
                    <button class="btn view-btn" onclick="check1(${user.id})">查看详细</button>
                    <button class="btn edit-btn" onclick="checkAndViewUserDetail(${user.id})">编辑</button>
                    <button class="btn delete-btn" onclick="checkDelete(${user.id})">删除</button>
                </td>
            </tr>
        `;
        tableBody.innerHTML += row;
    });

    updatePagination();
}

function updatePagination() {
    const totalPages = Math.ceil(allUsers.length / pageSize);
    document.getElementById('currentPage').textContent = `${currentPage} / ${totalPages}`;

    // Disable/enable previous and next buttons
    // document.getElementById('nextButton').disabled = (currentPage === totalPages);
}

async function handleSearch() {
    const searchTerm = document.getElementById('searchInput').value.toLowerCase();

    const filteredUsers = allUsers.filter(user =>
        user.username.toLowerCase().includes(searchTerm) ||
        // user.tel.toLowerCase().includes(searchTerm) ||
        user.id.toString().includes(searchTerm)
    );
    currentPage = 1;
    renderTable(filteredUsers);
}

function changePage(direction) {
    const totalPages = Math.ceil(allUsers.length / pageSize);
    currentPage += direction;
    if (currentPage < 1) currentPage = 1;
    if (currentPage > totalPages) currentPage = totalPages;
    renderTable(allUsers);
}

function showAddDialog() {
    const userData =JSON.parse(sessionStorage.getItem('user'));
    const role=parseInt(userData.role);
    if (role<0) {
        alert('您的权限被禁用，请联系超级管理员');
        return;
    }
    if (role>2) {
        alert('权限不足，只有管理员以上可以添加用户');
        return;
    }
    document.getElementById('dialogTitle').textContent = '添加用户';
    document.getElementById('addEditDialog').style.display = 'block';
    document.getElementById('userForm').reset();
        document.getElementById('userForm').addEventListener('submit', async function (e) {
            e.preventDefault();
            const formData = {
                username: document.getElementById('name').value,
                tel: document.getElementById('tel').value,
                email: document.getElementById('email').value,
                age: document.getElementById('age').value,
                gender: Array.from(document.getElementsByName('gender')).find(radio => radio.checked)?.value,
                idcard: document.getElementById('idcard').value,
                address: document.getElementById('address').value,
                account: document.getElementById('account').value,
                password: document.getElementById('password').value
            };
            console.log('formData:', formData);
            try {
                const {publicKey, keyId} = await fetchPublicKey();
                const encrypt = new JSEncrypt();
                encrypt.setPublicKey(publicKey);

                const encryptedData = Object.entries(formData).reduce((acc, [key, value]) => {
                    if (key !== 'address') {
                        acc[key] = encrypt.encrypt(value);
                        if (!acc[key]) {
                            throw new Error(`加密${key}失败`);
                        }
                    } else {
                        acc[key] = value;
                    }
                    return acc;
                }, {});

                const response = await fetch(`${SERVER_URL}/register`, {
                    method: 'POST',
                    credentials: 'include',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        keyId,
                        ...encryptedData
                    }),
                });

                if (!response.ok) {
                    const errorData = await response.json();
                    throw new Error(errorData.message || '注册请求失败，请重试');
                }

                const data = await response.json();
                if (data.success) {
                    confirm('用户添加成功');
                    closeDialog();
                    fetchUserData();
                    renderTable(allUsers);
                } else {
                    throw new Error(data.message);
                }
            } catch (error) {
                console.error('注册请求失败:', error);
                errorElement.textContent = error.message || '注册请求失败，请重试';
            }
        });

}

function closeDialog() {
    document.getElementById('addEditDialog').style.display = 'none';
}

function handleView(userId) {
    const user = allUsers.find(u => u.id === userId);
    const detailsHtml = `
        <p><strong>用户id:</strong> ${user.id}</p>
        <p><strong>用户名:</strong> ${user.username}</p>
        <p><strong>年龄:</strong> ${user.age || 'N/A'}</p>
        <p><strong>性别:</strong> ${user.gender === 'male' ? '男' : user.gender === 'female' ? '女' : user.gender}</p>
        <p><strong>身份证号:</strong> ${user.idcard || 'N/A'}</p>
        <p><strong>地址:</strong> ${user.address || 'N/A'}</p>
        <p><strong>手机号:</strong> ${user.tel}</p>
        <p><strong>邮箱:</strong> ${user.email || 'N/A'}</p>
<!--        <p><strong>Role:</strong> ${user.role || 'N/A'}</p>-->
    `;
    document.getElementById('userDetails').innerHTML = detailsHtml;
    document.getElementById('viewDialog').style.display = 'block';
}

function closeViewDialog() {
    document.getElementById('viewDialog').style.display = 'none';
}

async function handleEdit(userId) {
    const user = allUsers.find(u => u.id === userId);
    document.getElementById('dialogTitle').textContent = '编辑用户';
    document.getElementById('name').value = user.username;
    document.getElementById('tel').value = user.tel;
    document.getElementById('email').value = user.email || '';
    document.getElementById('age').value = user.age || '';
    const gender = user.gender;
    if (gender === 'male') {
        document.getElementById('genderMale').checked = true;
    } else if (gender === 'female') {
        document.getElementById('genderFemale').checked = true;
    }
    document.getElementById('idcard').value = user.idcard || '';
    document.getElementById('address').value = user.address || '';
    document.getElementById('addEditDialog').style.display = 'block';
    document.getElementById('account').value = user.account;
    document.getElementById('password').value = user.password;


    async function updateUserDetail(formData) {
        try {
            const response = await fetchAPI(`/update_user`, {
                method: 'POST',
                body: JSON.stringify({
                    ...formData
                }),
            });
            if (response.success) {
                alert('更新成功');
                closeDialog();
                fetchUserData();
                renderTable(allUsers);

            }
        } catch (error) {
            console.error('更新用户信息失败:', error);
            alert('更新失败，请重试');
        }
    }

    document.getElementById('userForm').addEventListener('submit', async function (e) {
        e.preventDefault();
        const formData = {
            id: userId,
            name: document.getElementById('name').value,
            tel: document.getElementById('tel').value,
            email: document.getElementById('email').value,
            age: document.getElementById('age').value,
            gender: Array.from(document.getElementsByName('gender')).find(radio => radio.checked)?.value,
            idcard: document.getElementById('idcard').value,
            address: document.getElementById('address').value,
            account: document.getElementById('account').value,
            password: document.getElementById('password').value
        };

        await updateUserDetail(formData);
    });


}

async function handleDelete(userId) {
    if (confirm('您确定要删除该用户吗？')) {
        const response = await fetchAPI(`/delete_user`, {
            method: 'POST',
            body: JSON.stringify({
                id: userId
            }),
        });
        if (response.success) {
            alert('用户删除成功');
            closeDialog();
            fetchUserData();
            renderTable(allUsers);
        }
    }
}


window.check1 = async function(userId) {
    try {
        const userData =JSON.parse(sessionStorage.getItem('user'));
        const role=parseInt(userData.role);
        if (role<0) {
            alert('您的权限被禁用，请联系超级管理员');
            return;
        }

        if (role<=2) {
            handleView(userId);
        } else {
            alert('权限不足，只有管理员以上可以查看详细个人信息');
        }
    } catch (error) {
        console.error('检查用户权限失败:', error);
        alert('权限检查失败，请重试');
    }
};


window.checkAndViewUserDetail = async function(userId) {
    try {
        const userData =JSON.parse(sessionStorage.getItem('user'));
        const role=parseInt(userData.role);
        if (role<0) {
            alert('您的权限被禁用，请联系超级管理员');
            return;
        }
        if (role==1) {
            handleEdit(userId);
        } else {
            alert('权限不足，只有超级管理员可以编辑用户');
        }
    } catch (error) {
        console.error('检查用户权限失败:', error);
        alert('权限检查失败，请重试');
    }
};
window.checkDelete = async function(userId) {
    try {
        const userData =JSON.parse(sessionStorage.getItem('user'));
        const role=parseInt(userData.role);
        if (role<0) {
            alert('您的权限被禁用，请联系超级管理员');
            return;
        }

        if (role==1) {
            handleDelete(userId);
        } else {
            alert('权限不足，只有超级管理员可以删除用户');
        }
    } catch (error) {
        console.error('检查用户权限失败:', error);
        alert('权限检查失败，请重试');
    }
};
// Initial data fetch
