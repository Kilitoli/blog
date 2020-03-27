<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<footer class="footer">
<div class="container">
<p>Copyright &copy; 2019.zhixiang </p>
</div>
<div id="gotop"><a class="gotop"></a></div>
</footer>
<div id="landlord">
    <div class="message" style="opacity:0"></div>
    <canvas id="live2d" width="280" height="250" class="live2d"></canvas>
</div>
<script src="../resources/admin/js/bootstrap.min.js"></script>
<script src="../resources/admin/js/jquery.ias.js"></script>
<script src="../resources/admin/js/scripts.js"></script>
<script type="text/javascript">
    var message_Path = '../resources/admin/live2d/'
    var home_Path = 'https://www.52ecy.cn/'  
</script>
<script type="text/javascript" src="../resources/admin/live2d/js/live2d.js"></script>
<script type="text/javascript" src="../resources/admin/live2d/js/message.js"></script>
<script type="text/javascript">
    loadlive2d("live2d", "../resources/admin/live2d/model/Pio/model.json");
    $(function(){

        /* 鼠标点击特效 */
        $("body").click(function(e) {

            var a = new Array("富强", "民主", "文明", "和谐", "自由", "平等", "公正" ,"法治", "爱国", "敬业", "诚信", "友善");
            var a_idx = Math.floor(Math.random()*(a.length+1));
            var $i = $("<span></span>").text(a[a_idx]);
            // a_idx = (a_idx + 1) % a.length;
            var x = e.pageX,
                y = e.pageY;
            $i.css({
                "z-index": 99999999,
                "top": y - 20,
                "left": x,
                "position": "absolute",
                "font-size": "16px",
                "color": "#ff0000"
            });
            $("body").append($i);
            $i.animate({
                    "top": y - 220,
                    "opacity": 0
                },
                1000,
                function() {
                    $i.remove();
                });
        });
    });
</script>
</body>
</html>