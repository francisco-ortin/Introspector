    function buttonClickedAction() {
      const detailsTags = document.getElementsByTagName("details");
      for (let i = 0; i < detailsTags.length; i++) {
        detailsTags[i].setAttribute("open", "true");
      }
      const button = document.getElementById("expandButton");
      button.remove();
    }
