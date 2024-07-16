function toggleList(id) {
    const list = document.getElementById(id);
    const displayStyle = list.style.display;
    list.style.display = displayStyle === 'none' || displayStyle === '' ? 'block' : 'none';
}
