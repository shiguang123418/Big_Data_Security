document.addEventListener('DOMContentLoaded', async () => {
    const form = document.getElementById('form');
    const errorElement = document.getElementById('error');
    const inputs = {
        email: document.getElementById('email'),
        password: document.getElementById('password'),
        account: document.getElementById('account'),
        username: document.getElementById('username'),
        age: document.getElementById('age'),
        gender: document.getElementsByName('gender'),
        tel: document.getElementById('tel'),
        idcard: document.getElementById('idcard'),
        address: document.getElementById('address')
    };

    await checkSession();

    form.addEventListener('submit', handleSubmit);

    async function handleSubmit(event) {
        event.preventDefault();
        errorElement.textContent = '';

        const formData = {
            email: inputs.email.value,
            password: inputs.password.value,
            account: inputs.account.value,
            username: inputs.username.value,
            age: inputs.age.value.toString(),
            gender: Array.from(inputs.gender).find(radio => radio.checked)?.value,
            tel: inputs.tel.value,
            idcard: inputs.idcard.value,
            address: inputs.address.value
        };


        if (Object.values(formData).some(value => !value)) {
            errorElement.textContent = '所有字段都是必填的';
            return;
        }

        try {
            const { publicKey, keyId } = await fetchPublicKey();
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
                window.location.href = '/login';
            } else {
                throw new Error(data.message);
            }
        } catch (error) {
            console.error('注册请求失败:', error);
            errorElement.textContent = error.message || '注册请求失败，请重试';
        }
    }


});

