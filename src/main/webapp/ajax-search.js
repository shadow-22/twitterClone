function searchUsers(pageType) {
    var searchKeyword = document.getElementById("searchKeyword").value;
    var resultsElementId = pageType === 'secure' ? "searchResultsSecure" : "searchResultsIndex";

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                var searchResults = JSON.parse(xhr.responseText);
                displaySearchResults(searchResults, resultsElementId);
            } else {
                console.error("Error: " + xhr.statusText);
            }
        }
    };

    xhr.open("POST", "search", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.send("searchKeyword=" + encodeURIComponent(searchKeyword));
}

function displaySearchResults(searchResults, resultsElementId) {
    var searchResultsDiv = document.getElementById(resultsElementId);

    // Clear previous search results
    searchResultsDiv.innerHTML = "";

    if (searchResults.length === 0) {
        searchResultsDiv.innerHTML = "No results found.";
        return;
    }

    for (var i = 0; i < searchResults.length; i++) {
        var userLink = document.createElement("a");
        userLink.href = "secure-page.jsp?username=" + encodeURIComponent(searchResults[i].username);
        userLink.textContent = searchResults[i].username;

        var listItem = document.createElement("li");
        listItem.appendChild(userLink);

        searchResultsDiv.appendChild(listItem);
    }
}
