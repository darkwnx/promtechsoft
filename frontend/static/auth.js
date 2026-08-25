// Authentication frontend module. The workflow patches index.html to load this file.
(() => {
    'use strict';

    const API_URL = window.AUTH_API_URL || (
        window.location.hostname === 'localhost' || window.location.hostname === '127.0.0.1'
            ? 'http://localhost:8080/api/v1'
            : '/api/v1'
    );

    const byId = (id) => document.getElementById(id);

    function openLogin() {
        const modal = byId('loginModal');
        if (!modal) return;
        modal.classList.add('active');
        document.body.style.overflow = 'hidden';
        setTimeout(() => byId('loginUsername')?.focus(), 50);
    }

    function openRegister() {
        const modal = byId('registerModal');
        if (!modal) return;
        modal.classList.add('active');
        document.body.style.overflow = 'hidden';
        setTimeout(() => byId('regUsername')?.focus(), 50);
    }

    function closeModal(id) {
        const modal = byId(id);
        if (modal) modal.classList.remove('active');
        if (!document.querySelector('.modal-overlay.active')) document.body.style.overflow = '';
    }

    function notify(message, error = false) {
        document.querySelector('.notification-toast')?.remove();
        const el = document.createElement('div');
        el.className = 'notification-toast' + (error ? ' error' : '');
        el.textContent = message;
        document.body.appendChild(el);
        setTimeout(() => el.remove(), 4000);
    }

    async function apiError(response, fallback) {
        try {
            const data = await response.json();
            return data.message || data.error || fallback;
        } catch (_) {
            return fallback;
        }
    }

    async function handleLogin(event) {
        event?.preventDefault();
        const username = byId('loginUsername')?.value.trim();
        const password = byId('loginPassword')?.value || '';
        if (!username || !password) return notify('Заполните имя пользователя и пароль.', true);
        try {
            const response = await fetch(`${API_URL}/auth/login`, { method: 'POST', headers: { 'Content-Type': 'application/json', 'Accept': 'application/json' }, body: JSON.stringify({ username, password }) });
            if (!response.ok) throw new Error(await apiError(response, 'Неверное имя пользователя или пароль.'));
            const data = await response.json();
            if (!data.token) throw new Error('Backend не вернул JWT token.');
            localStorage.setItem('token', data.token);
            localStorage.setItem('user', JSON.stringify(data));
            updateAuthUI(true);
            closeModal('loginModal');
            byId('loginForm')?.reset();
            notify(`Добро пожаловать, ${data.firstName || data.username || username}!`);
        } catch (error) {
            console.error('Login:', error);
            notify(error.message || 'Ошибка входа.', true);
        }
    }

    async function handleRegister(event) {
        event?.preventDefault();
        const username = byId('regUsername')?.value.trim();
        const email = byId('regEmail')?.value.trim();
        const firstName = byId('regFirstName')?.value.trim() || '';
        const lastName = byId('regLastName')?.value.trim() || '';
        const password = byId('regPassword')?.value || '';
        if (!username || !email || !password) return notify('Заполните обязательные поля.', true);
        try {
            const response = await fetch(`${API_URL}/auth/register`, { method: 'POST', headers: { 'Content-Type': 'application/json', 'Accept': 'application/json' }, body: JSON.stringify({ username, email, firstName, lastName, password }) });
            if (!response.ok) throw new Error(await apiError(response, 'Не удалось зарегистрировать пользователя.'));
            const data = await response.json();
            if (!data.token) throw new Error('Backend не вернул JWT token после регистрации.');
            localStorage.setItem('token', data.token);
            localStorage.setItem('user', JSON.stringify(data));
            updateAuthUI(true);
            closeModal('registerModal');
            byId('registerForm')?.reset();
            notify(`Регистрация успешна. Добро пожаловать, ${data.firstName || data.username || username}!`);
        } catch (error) {
            console.error('Registration:', error);
            notify(error.message || 'Ошибка регистрации.', true);
        }
    }

    function logout() {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        updateAuthUI(false);
        notify('Вы вышли из системы.');
    }

    function updateAuthUI(loggedIn) {
        const box = byId('authButtons');
        if (!box) return;
        if (!loggedIn) {
            box.innerHTML = '<button type="button" class="btn-sm btn-outline-sm" id="loginButton">Войти</button><button type="button" class="btn-sm btn-primary-sm" id="registerButton">Регистрация</button>';
            byId('loginButton')?.addEventListener('click', openLogin);
            byId('registerButton')?.addEventListener('click', openRegister);
            return;
        }
        let user = {};
        try { user = JSON.parse(localStorage.getItem('user') || '{}'); } catch (_) {}
        const name = user.firstName || user.username || 'Пользователь';
        box.innerHTML = `<span class="user-name">👤 ${String(name).replace(/[&<>"']/g, c => ({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#039;'}[c]))}</span><button type="button" class="btn-sm btn-outline-sm" id="logoutButton">Выйти</button>`;
        byId('logoutButton')?.addEventListener('click', logout);
    }

    function init() {
        byId('loginForm')?.addEventListener('submit', handleLogin);
        byId('registerForm')?.addEventListener('submit', handleRegister);
        byId('openRegisterFromLogin')?.addEventListener('click', (e) => { e.preventDefault(); closeModal('loginModal'); openRegister(); });
        byId('openLoginFromRegister')?.addEventListener('click', (e) => { e.preventDefault(); closeModal('registerModal'); openLogin(); });
        document.querySelectorAll('.modal-overlay').forEach(modal => modal.addEventListener('click', e => { if (e.target === modal) closeModal(modal.id); }));
        document.addEventListener('keydown', e => { if (e.key === 'Escape') document.querySelectorAll('.modal-overlay.active').forEach(m => closeModal(m.id)); });
        updateAuthUI(!!localStorage.getItem('token'));
    }

    window.openLogin = openLogin;
    window.openRegister = openRegister;
    window.closeModal = closeModal;
    window.handleLogin = handleLogin;
    window.handleRegister = handleRegister;
    window.logout = logout;

    if (document.readyState === 'loading') document.addEventListener('DOMContentLoaded', init, { once: true });
    else init();
})();
