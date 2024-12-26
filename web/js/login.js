document.addEventListener('DOMContentLoaded', async () => {

    const form = document.getElementById('form');
    const accountInput = document.getElementById('account');
    const passwordInput = document.getElementById('password');
    const errorElement = document.getElementById('error');
    await checkSession();

    form.addEventListener('submit', handleSubmit);

    async function handleSubmit(e) {

        e.preventDefault();
        errorElement.textContent = '';
        setSessionCookie();
        const account = accountInput.value;
        const password = passwordInput.value;

        if (!account|| !password) {
            errorElement.textContent = '请填写所有字段';
            return;
        }

        const data=await fetchPublicKey();
        window.publicKey = data.publicKey;
        window.publicKeyId = data.keyId;
        try {
            const encrypt = new JSEncrypt();
            encrypt.setPublicKey(window.publicKey);

            const encryptedaccount = encrypt.encrypt(account, window.publicKey);
            const encryptedPassword = encrypt.encrypt(password, window.publicKey);

            if (!encryptedaccount || !encryptedPassword) {
                errorElement.textContent = '加密失败，请重试';
                errorElement.style.display = 'block';
                return;
            }

            const response = await fetch(`${SERVER_URL}/login`, {
                method: 'POST',
                credentials: 'include',
                headers: {
                    "sessionID": document.cookie.split('=')[1],
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    account: encryptedaccount,
                    password: encryptedPassword,
                    keyId: window.publicKeyId,
                }),
            });

            const data = await response.json();
            if (data.success) {
                window.location.href = '/index';
            } else {
                errorElement.textContent = data.message;
                errorElement.style.display = 'block';
            }
        } catch (error) {
            console.error('登录请求失败:', error);
        }
    }

});