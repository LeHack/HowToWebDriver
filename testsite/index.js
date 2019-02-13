var Kitty = new function() {
	this.visible = false;
	var pos1 = 0, pos2 = 0, pos3 = 0, pos4 = 0;

	this.show = function() {
		if (this.visible)
			return;

		let elem = document.createElement('span');
		elem.id = "kitty";
		elem.className = "kittyImg";
		elem.onmousedown = this.dragMouseDown;

		document.getElementById("kittyStart").appendChild(elem);
		this.visible = true;
	}

	this.hide = function() {
		document.getElementById("kitty").remove();
		this.visible = false;
	}

	this.toggle = function() {
		if (this.visible)
			this.hide();
		else
			this.show();
	}

	let dragKitty = function(e) {
		e = e || window.event;
		e.preventDefault();
		// calculate the new cursor position:
		pos1 = pos3 - e.clientX;
		pos2 = pos4 - e.clientY;
	    pos3 = e.clientX;
	    pos4 = e.clientY;
	    // set the element's new position:
	    let elem = document.getElementById("kitty");
	    elem.style.top = (elem.offsetTop - pos2) + "px";
	    elem.style.left = (elem.offsetLeft - pos1) + "px";
	}
	this.dragKitty = dragKitty;

	let closeDrag = function() {
	    // stop moving when mouse button is released:
		document.onmouseup = null;
		document.onmousemove = null;
	}
	this.closeDrag = closeDrag;

	this.dragMouseDown = function(e) {
	    e = e || window.event;
	    e.preventDefault();
	    // get the mouse cursor position at startup:
	    pos3 = e.clientX;
	    pos4 = e.clientY;
	    // hang tight
	    document.onmousemove = dragKitty;
	    document.onmouseup = closeDrag;
	}

	this.respond = function(e) {
		if (e.keyCode === 13) {
			let old = document.getElementsByClassName("kittyName thing");
			if (old.length > 0)
				old[0].remove();

			let value = document.getElementById("nameInput").value;
			let elem = document.createElement('span');
			elem.className = 'kittyName thing';
			elem.textContent = "That's nice, I like " + value;
			document.getElementsByClassName("kittyName")[0].appendChild(elem);
		}
	}

	this.change = function(what) {
		let elem = document.getElementById("kitty");
		if (elem) {
			elem.style.display = "none";
			elem.style.backgroundImage = "url('" + what + "')";
			let loader = document.createElement('span');
			loader.id = "loading";
			loader.className = "loader";
			loader.appendChild( document.createTextNode("Loading...") );
			document.body.appendChild(loader);
			setTimeout(this.hideLoading, 2500);
		}
	}

	this.hideLoading = function() {
		let loader = document.getElementById("loading");
		if (loader) {
			loader.remove();
		}
		let elem = document.getElementById("kitty");
		if (elem) {
			elem.style.display = "block";
		}
	}
};

document.getElementById("nameInput").addEventListener("keyup", Kitty.respond);
