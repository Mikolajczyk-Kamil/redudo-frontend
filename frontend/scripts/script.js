window.search = function search(filter) {
    let item, title, author, i, titleValue, authorValue;
    filter = filter.toUpperCase();
    item = document.getElementsByClassName("item");

    for (i = 0; i < item.length; i++) {
        title = item[i].querySelector(".itemTitle");
        author = item[i].querySelector(".itemAuthor");
        if (title || author) {
            titleValue = title.textContent || title.innerText;
            authorValue = author.textContent || author.innerText;
            if (titleValue.toUpperCase().indexOf(filter) > -1 || authorValue.toUpperCase().indexOf(filter) > -1) {
                item[i].style.display = "";
            } else {
                item[i].style.display = "none";
            }
        }
    }
}