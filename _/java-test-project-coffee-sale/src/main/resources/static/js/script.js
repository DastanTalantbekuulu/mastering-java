// Activate active navigation
document.addEventListener('DOMContentLoaded', function() {
    const currentPath = window.location.pathname;
    const navLinks = document.querySelectorAll('.nav-link');

    navLinks.forEach(li => {
        const link = li.getAttribute('href');
        if (link === currentPath.slice(0, link.length)) {
            li.classList.add('active');
        }
    });
});