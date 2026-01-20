const urlParams = new URLSearchParams(window.location.search)
let offset = urlParams.has("offset") ? Number(urlParams.get("offset")) : 0 

function nextPage(){
	offset += 5
	window.location.href = `/product?offset=${offset}`
}
function previousPage(){
	if(offset > 1){
		offset -= 5			
		window.location.href = `/product?offset=${offset}`
	}
}
