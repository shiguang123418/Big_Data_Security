document.addEventListener('DOMContentLoaded', () => {
    const fileInput = document.getElementById('file-upload');
    const recoverButton = document.getElementById('recover');
    const btnText = recoverButton.querySelector('.btn-text');
    const spinner = recoverButton.querySelector('.spinner');
    const fileNameDisplay = document.querySelector('.file-name');

    // Handle file selection
    fileInput.addEventListener('change', () => {
        const file = fileInput.files[0];
        if (file) {
            fileNameDisplay.textContent = file.name;
            recoverButton.disabled = false;
        } else {
            fileNameDisplay.textContent = '';
            recoverButton.disabled = true;
        }
    });

    // Handle recovery process
    recoverButton.addEventListener('click', async () => {
        const file = fileInput.files[0];
        if (!file) {
            alert("请选择文件！");
            return;
        }

        try {
            recoverButton.classList.add('loading');
            btnText.textContent = '正在恢复...';

            const formData = new FormData();
            formData.append('file', file);

            const response = await fetch(SERVER_URL+'/recover', {
                method: 'POST',
                body: formData,
                credentials: 'include'
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const result = await response.text();

            recoverButton.classList.add('success');
            btnText.textContent = '恢复成功！';

            setTimeout(() => {
                recoverButton.classList.remove('loading', 'success');
                btnText.textContent = '上传并恢复';
                fileInput.value = '';
                fileNameDisplay.textContent = '';
                recoverButton.disabled = true;
            }, 2000);

        } catch (error) {
            console.error('Recovery failed:', error);
            recoverButton.classList.add('error');
            btnText.textContent = '恢复失败，请重试';

            setTimeout(() => {
                recoverButton.classList.remove('loading', 'error');
                btnText.textContent = '上传并恢复';
            }, 2000);
        }
    });
});