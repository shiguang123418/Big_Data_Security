document.addEventListener('DOMContentLoaded', async function() {
    const profileForm = document.getElementById('profileForm');
    const user = await fetchCurrentUser();
    // 获取个人信息
    async function fetchProfile() {

        try {
            document.getElementById('name').value = user.username;
            document.getElementById('email').value = user.email;
            document.querySelector(`input[name="gender"][value="${user.gender}"]`).checked = true;
            document.getElementById('age').value = user.age;
            document.getElementById('tel').value = user.tel;
            document.getElementById('idcard').value = user.idcard;
            document.getElementById('address').value = user.address;
            document.getElementById('account').value = user.account;
            document.getElementById('password').value = user.password;
        } catch (error) {
            console.error('获取个人信息失败:', error);
        }
    }

    // 更新个人信息
    async function updateProfile(formData) {
        try {
            const data = await fetchAPI('/update_user', {
                method: 'POST',
                body: JSON.stringify({
                    id: user.id,
                    name: formData.get('name'),
                    email: formData.get('email'),
                    gender: formData.get('gender'),
                    age: formData.get('age'),
                    tel: formData.get('tel'),
                    idcard: formData.get('idcard'),
                    address: formData.get('address'),
                    account: formData.get('account'),
                    password: formData.get('password'),
                }),
            });

            if (data.success) {
                // 更新成功后刷新父窗口中的用户信息显示
                alert('更新成功');
            } else {
                alert('更新失败: ' + data.message);
            }
        } catch (error) {
            console.error('更新个人信息失败:', error);
            alert('更新失败，请稍后重试');
        }
    }

    // 提交表单处理
    profileForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const formData = new FormData(profileForm);
        await updateProfile(formData);
    });

    // 初始加载个人信息
    fetchProfile();
});

