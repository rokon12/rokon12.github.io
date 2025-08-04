// Terminal-style effects and animations
document.addEventListener('DOMContentLoaded', function() {
    // Matrix rain effect for hero section
    function createMatrixRain() {
        const canvas = document.createElement('canvas');
        canvas.id = 'matrix-rain';
        canvas.style.cssText = 'position:fixed;top:0;left:0;width:100%;height:100%;z-index:-1;opacity:0.05;pointer-events:none;';
        document.body.appendChild(canvas);
        
        const ctx = canvas.getContext('2d');
        canvas.width = window.innerWidth;
        canvas.height = window.innerHeight;
        
        const matrix = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789@#$%^&*()*&^%+-/~{[|`]}";
        const matrixArray = matrix.split("");
        
        const fontSize = 10;
        const columns = canvas.width / fontSize;
        
        const drops = [];
        for(let x = 0; x < columns; x++) {
            drops[x] = 1;
        }
        
        function draw() {
            ctx.fillStyle = 'rgba(0, 0, 0, 0.04)';
            ctx.fillRect(0, 0, canvas.width, canvas.height);
            
            ctx.fillStyle = '#667eea';
            ctx.font = fontSize + 'px monospace';
            
            for(let i = 0; i < drops.length; i++) {
                const text = matrixArray[Math.floor(Math.random() * matrixArray.length)];
                ctx.fillText(text, i * fontSize, drops[i] * fontSize);
                
                if(drops[i] * fontSize > canvas.height && Math.random() > 0.975) {
                    drops[i] = 0;
                }
                drops[i]++;
            }
        }
        
        setInterval(draw, 35);
        
        window.addEventListener('resize', () => {
            canvas.width = window.innerWidth;
            canvas.height = window.innerHeight;
        });
    }
    
    // Terminal typing effect
    function typeWriter(element, text, speed = 50) {
        let i = 0;
        element.innerHTML = '';
        element.style.borderRight = '3px solid #667eea';
        element.style.animation = 'blink 1s infinite';
        
        function type() {
            if (i < text.length) {
                element.innerHTML += text.charAt(i);
                i++;
                setTimeout(type, speed);
            } else {
                element.style.borderRight = 'none';
                element.style.animation = 'none';
            }
        }
        type();
    }
    
    // Add typing effect to hero title
    const heroTitle = document.querySelector('.hero-title');
    if (heroTitle) {
        const originalText = heroTitle.textContent;
        typeWriter(heroTitle, originalText, 75);
    }
    
    // Command palette (Cmd+K or Ctrl+K)
    let commandPaletteOpen = false;
    document.addEventListener('keydown', function(e) {
        if ((e.metaKey || e.ctrlKey) && e.key === 'k') {
            e.preventDefault();
            toggleCommandPalette();
        }
        if (e.key === 'Escape' && commandPaletteOpen) {
            closeCommandPalette();
        }
    });
    
    function toggleCommandPalette() {
        let palette = document.getElementById('command-palette');
        if (!palette) {
            createCommandPalette();
            palette = document.getElementById('command-palette');
        }
        
        if (commandPaletteOpen) {
            closeCommandPalette();
        } else {
            palette.style.display = 'block';
            palette.querySelector('input').focus();
            commandPaletteOpen = true;
        }
    }
    
    function closeCommandPalette() {
        const palette = document.getElementById('command-palette');
        if (palette) {
            palette.style.display = 'none';
            commandPaletteOpen = false;
        }
    }
    
    function createCommandPalette() {
        const palette = document.createElement('div');
        palette.id = 'command-palette';
        palette.innerHTML = `
            <div class="command-palette-backdrop" onclick="closeCommandPalette()"></div>
            <div class="command-palette-modal">
                <input type="text" placeholder="Type a command or search..." class="command-input">
                <div class="command-results">
                    <div class="command-section">
                        <div class="command-section-title">Quick Actions</div>
                        <div class="command-item" data-action="home">
                            <span class="command-icon">🏠</span>
                            <span>Go Home</span>
                            <span class="command-shortcut">Alt+H</span>
                        </div>
                        <div class="command-item" data-action="archive">
                            <span class="command-icon">📚</span>
                            <span>View Archive</span>
                            <span class="command-shortcut">Alt+A</span>
                        </div>
                        <div class="command-item" data-action="tags">
                            <span class="command-icon">🏷️</span>
                            <span>Browse Tags</span>
                            <span class="command-shortcut">Alt+T</span>
                        </div>
                        <div class="command-item" data-action="search">
                            <span class="command-icon">🔍</span>
                            <span>Search Articles</span>
                            <span class="command-shortcut">Alt+S</span>
                        </div>
                        <div class="command-item" data-action="random">
                            <span class="command-icon">🎲</span>
                            <span>Random Post</span>
                            <span class="command-shortcut">Alt+R</span>
                        </div>
                        <div class="command-item" data-action="theme">
                            <span class="command-icon">🎨</span>
                            <span>Toggle Theme</span>
                            <span class="command-shortcut">Alt+D</span>
                        </div>
                    </div>
                </div>
            </div>
        `;
        document.body.appendChild(palette);
        
        // Add search functionality
        const input = palette.querySelector('.command-input');
        const items = palette.querySelectorAll('.command-item');
        
        input.addEventListener('input', function(e) {
            const query = e.target.value.toLowerCase();
            items.forEach(item => {
                const text = item.textContent.toLowerCase();
                item.style.display = text.includes(query) ? 'flex' : 'none';
            });
        });
        
        // Add click handlers
        items.forEach(item => {
            item.addEventListener('click', function() {
                const action = this.dataset.action;
                executeCommand(action);
                closeCommandPalette();
            });
        });
        
        // Add keyboard navigation
        let selectedIndex = -1;
        input.addEventListener('keydown', function(e) {
            const visibleItems = Array.from(items).filter(item => item.style.display !== 'none');
            
            if (e.key === 'ArrowDown') {
                e.preventDefault();
                selectedIndex = (selectedIndex + 1) % visibleItems.length;
                updateSelection(visibleItems, selectedIndex);
            } else if (e.key === 'ArrowUp') {
                e.preventDefault();
                selectedIndex = selectedIndex <= 0 ? visibleItems.length - 1 : selectedIndex - 1;
                updateSelection(visibleItems, selectedIndex);
            } else if (e.key === 'Enter' && selectedIndex >= 0) {
                e.preventDefault();
                const action = visibleItems[selectedIndex].dataset.action;
                executeCommand(action);
                closeCommandPalette();
            }
        });
    }
    
    function updateSelection(items, index) {
        items.forEach((item, i) => {
            if (i === index) {
                item.classList.add('selected');
                item.scrollIntoView({ block: 'nearest' });
            } else {
                item.classList.remove('selected');
            }
        });
    }
    
    function executeCommand(action) {
        switch(action) {
            case 'home':
                window.location.href = '{{ site.baseurl }}/';
                break;
            case 'archive':
                window.location.href = '{{ site.baseurl }}/archive/';
                break;
            case 'tags':
                window.location.href = '{{ site.baseurl }}/tags/';
                break;
            case 'search':
                window.location.href = '{{ site.baseurl }}/search/';
                break;
            case 'random':
                // Get random post
                fetch('{{ site.baseurl }}/api/posts.json')
                    .then(r => r.json())
                    .then(posts => {
                        const random = posts[Math.floor(Math.random() * posts.length)];
                        window.location.href = random.url;
                    });
                break;
            case 'theme':
                toggleTheme();
                break;
        }
    }
    
    // Konami code easter egg
    const konamiCode = ['ArrowUp', 'ArrowUp', 'ArrowDown', 'ArrowDown', 'ArrowLeft', 'ArrowRight', 'ArrowLeft', 'ArrowRight', 'b', 'a'];
    let konamiIndex = 0;
    
    document.addEventListener('keydown', function(e) {
        if (e.key === konamiCode[konamiIndex]) {
            konamiIndex++;
            if (konamiIndex === konamiCode.length) {
                activateEasterEgg();
                konamiIndex = 0;
            }
        } else {
            konamiIndex = 0;
        }
    });
    
    function activateEasterEgg() {
        document.body.classList.add('easter-egg-active');
        createMatrixRain();
        
        // Show achievement notification
        const notification = document.createElement('div');
        notification.className = 'achievement-notification';
        notification.innerHTML = `
            <div class="achievement-icon">🏆</div>
            <div class="achievement-text">
                <div class="achievement-title">Achievement Unlocked!</div>
                <div class="achievement-desc">Konami Code Master</div>
            </div>
        `;
        document.body.appendChild(notification);
        
        setTimeout(() => {
            notification.classList.add('show');
        }, 100);
        
        setTimeout(() => {
            notification.classList.remove('show');
            setTimeout(() => notification.remove(), 500);
        }, 3000);
    }
    
    // Console easter eggs
    console.log('%c' + `
    ╔════════════════════════════════════════╗
    ║                                        ║
    ║     Welcome to the Developer Zone!     ║
    ║                                        ║
    ║     🚀 Curious developer detected!     ║
    ║                                        ║
    ║     Try: Konami Code (↑↑↓↓←→←→BA)     ║
    ║     Press: Cmd/Ctrl + K for palette    ║
    ║                                        ║
    ╚════════════════════════════════════════╝
    `, 'color: #667eea; font-family: monospace; font-size: 12px;');
    
    // Add glitch effect on hover for certain elements
    document.querySelectorAll('.post-title a, .tag-link').forEach(element => {
        element.addEventListener('mouseenter', function() {
            this.classList.add('glitch');
        });
        element.addEventListener('mouseleave', function() {
            setTimeout(() => this.classList.remove('glitch'), 300);
        });
    });
    
    // Initialize on page load
    if (document.querySelector('.hero-section')) {
        createMatrixRain();
    }
});

// Theme management
function toggleTheme() {
    const currentTheme = localStorage.getItem('theme') || 'dark';
    const themes = ['dark', 'dracula', 'nord', 'monokai', 'synthwave'];
    const currentIndex = themes.indexOf(currentTheme);
    const nextTheme = themes[(currentIndex + 1) % themes.length];
    
    document.documentElement.setAttribute('data-theme', nextTheme);
    localStorage.setItem('theme', nextTheme);
    
    // Show theme notification
    showNotification(`Theme: ${nextTheme.charAt(0).toUpperCase() + nextTheme.slice(1)}`);
}

function showNotification(message) {
    const notification = document.createElement('div');
    notification.className = 'theme-notification';
    notification.textContent = message;
    document.body.appendChild(notification);
    
    setTimeout(() => notification.classList.add('show'), 100);
    setTimeout(() => {
        notification.classList.remove('show');
        setTimeout(() => notification.remove(), 500);
    }, 2000);
}