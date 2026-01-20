document.addEventListener('DOMContentLoaded', () => {    
    const urlParams = new URLSearchParams(window.location.search);

    
    if (urlParams.get('err') === 'true' && urlParams.has('msg')) {
        const msg = urlParams.get('msg');
        
        if (msg.trim() !== "") {
            alert(decodeURIComponent(msg)); // decode limpa acentos e espaços vindos da URL
        }

        const cleanUrl = window.location.protocol + "//" + window.location.host + window.location.pathname;
        window.history.replaceState({path: cleanUrl}, '', cleanUrl);
    }
});