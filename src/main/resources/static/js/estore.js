$(document).ready(function () {
  $("#logout").on("click", function () {
    if (!confirm("Bạn muốn đăng xuất không!")) return false;
  });

  $(".add-like").on("click", function () {
    var id = $(this).closest("[data-id]").attr("data-id");
    var url = "/product/like/" + id;
    fetch(url)
      .then((resp) => resp.json())
      .then((json) => {
        alert("Đã Like -- " + json);
      });
  });

  // edit photo
  $("[name=photo_file]").on("change", function () {
    showImage(this, "#photo_img");
  });

  $("input[name=image_file]").on("change", function () {
    showImage(this, "#image_img");
  });

  showImage = function (fileSelector, imageSelector) {
    let file = $(fileSelector).get(0).files[0];
    let fileReader = new FileReader();
    fileReader.onload = function () {
      $(imageSelector).attr("src", fileReader.result);
    };
    fileReader.onerror = function () {
      alert(fileReader.error);
    };
    fileReader.readAsDataURL(file);
  };
});
