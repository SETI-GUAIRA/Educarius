var LambariusPro = LambariusPro || {}

LambariusPro.ImageOverlay = (function () {
  class ImageOverlay {
    constructor() {
      this.img = document.querySelectorAll('.img-container')
      this.bigImg = document.querySelectorAll('.bigImg')
      this.imgThumb = document.querySelectorAll('.img-thumb')
    }
    enable() {
      for (let i = 0; i < this.img.length; i++) {
        this.img[i].addEventListener("mouseover", () => {
          this.imgThumb[i].style.opacity = 0.5
          this.bigImg[i].style.display = "block"
        })

        this.img[i].addEventListener("mouseout", () => {
          this.imgThumb[i].style.opacity = 1
          this.bigImg[i].style.display = "none"
        })
      }
    }
  }

  return ImageOverlay
}())

$(() => {
  var confirmDelete = new LambariusPro.ImageOverlay();
  confirmDelete.enable();
});

