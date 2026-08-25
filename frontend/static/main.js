document.addEventListener('DOMContentLoaded', () => {
    // ========== БУРГЕР-МЕНЮ ==========
    const burger = document.getElementById('burger');
    const navMenu = document.getElementById('navMenu');

    if (burger && navMenu) {
        burger.addEventListener('click', () => {
            burger.classList.toggle('active');
            navMenu.classList.toggle('active');
            document.body.style.overflow = navMenu.classList.contains('active') ? 'hidden' : '';
        });

        navMenu.querySelectorAll('a').forEach(link => {
            link.addEventListener('click', () => {
                burger.classList.remove('active');
                navMenu.classList.remove('active');
                document.body.style.overflow = '';
            });
        });
    }

    // ========== ШАПКА ПРИ СКРОЛЛЕ ==========
    const header = document.getElementById('header');
    if (header) {
        window.addEventListener('scroll', () => {
            header.classList.toggle('scrolled', window.scrollY > 50);
        });
    }

    // ========== КНОПКА НАВЕРХ ==========
    const scrollTopBtn = document.getElementById('scrollTop');
    if (scrollTopBtn) {
        window.addEventListener('scroll', () => {
            scrollTopBtn.classList.toggle('visible', window.scrollY > 500);
        });

        scrollTopBtn.addEventListener('click', () => {
            window.scrollTo({ top: 0, behavior: 'smooth' });
        });
    }

    // ========== АНИМАЦИИ ПОЯВЛЕНИЯ ==========
    const animatedElements = document.querySelectorAll('.fade-in');
    if ('IntersectionObserver' in window && animatedElements.length > 0) {
        const observer = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    entry.target.classList.add('visible');
                    observer.unobserve(entry.target);
                }
            });
        }, { threshold: 0.1, rootMargin: '0px 0px -50px 0px' });

        animatedElements.forEach(el => observer.observe(el));
    }

    // ========== МОДАЛЬНОЕ ОКНО С GOOGLE ФОРМОЙ ==========
    const modal = document.getElementById('applicationModal');
    const closeBtn = document.querySelector('.close-modal');

    function openApplicationModal() {
        if (modal) {
            modal.style.display = 'block';
            document.body.style.overflow = 'hidden';
        }
    }

    function closeApplicationModal() {
        if (modal) {
            modal.style.display = 'none';
            document.body.style.overflow = '';
        }
    }

    if (closeBtn) {
        closeBtn.addEventListener('click', closeApplicationModal);
    }

    window.addEventListener('click', function(event) {
        if (event.target === modal) {
            closeApplicationModal();
        }
    });

    // Привязываем кнопку "Подать заявление"
    const contactSection = document.querySelector('#contact');
    if (contactSection) {
        const applyButton = contactSection.querySelector('.btn');
        if (applyButton) {
            applyButton.addEventListener('click', function(e) {
                e.preventDefault();
                openApplicationModal();
            });
        }
    }

    // ========== ЗАКРЫТИЕ ПО ESCAPE ==========
    document.addEventListener('keydown', (e) => {
        if (e.key !== 'Escape') return;

        if (navMenu && burger && navMenu.classList.contains('active')) {
            burger.classList.remove('active');
            navMenu.classList.remove('active');
            document.body.style.overflow = '';
        }

        if (modal && modal.style.display === 'block') {
            closeApplicationModal();
        }
    });
});