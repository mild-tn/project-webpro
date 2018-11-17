<%-- 
    Document   : Contact
    Created on : Nov 15, 2018, 6:07:55 PM
    Author     : maysmiler
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="include/css/Contact.css"/>
        <meta charset="utf-8">
        <title>CONTACT US</title>
        <link rel="stylesheet" href="include/css/style-page.css"/> 
        <link href="https://fonts.googleapis.com/css?family=Fjalla+One|Oswald|Pathway+Gothic+One|Play|Titillium+Web" rel="stylesheet">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script src="https://use.fontawesome.com/622cc25ba3.js"></script>
        <link rel="icon" type="image/png" sizes="64x64" href="images/oie_transparent.png">
    </head>
    <body style="background-color: #000">
        <jsp:include page="include/NavBarBackColor.jsp" />
        <script>
            $('textarea').blur(function () {
                $('#hire textarea').each(function () {
                    $this = $(this);
                    if (this.value != '') {
                        $this.addClass('focused');
                        $('textarea + label + span').css({'opacity': 1});
                    } else {
                        $this.removeClass('focused');
                        $('textarea + label + span').css({'opacity': 0});
                    }
                });
            });

            $('#hire .field:first-child input').blur(function () {
                $('#hire .field:first-child input').each(function () {
                    $this = $(this);
                    if (this.value != '') {
                        $this.addClass('focused');
                        $('.field:first-child input + label + span').css({'opacity': 1});
                    } else {
                        $this.removeClass('focused');
                        $('.field:first-child input + label + span').css({'opacity': 0});
                    }
                });
            });

            $('#hire .field:nth-child(2) input').blur(function () {
                $('#hire .field:nth-child(2) input').each(function () {
                    $this = $(this);
                    if (this.value != '') {
                        $this.addClass('focused');
                        $('.field:nth-child(2) input + label + span').css({'opacity': 1});
                    } else {
                        $this.removeClass('focused');
                        $('.field:nth-child(2) input + label + span').css({'opacity': 0});
                    }
                });
            });
        </script>
        <div class="container">
            <div class="row">
                <div class="col-12">

                    <section id="hire">
                        <h1>Contact Me</h1>

                        <form id="form">
                            <div class="field name-box">
                                <input type="text" id="name" placeholder="Who Are You?"/>
                                <label for="name">Name</label>
                                <span class="ss-icon">check</span>
                            </div>

                            <div class="field email-box">
                                <input type="text" id="email" placeholder="name@email.com"/>
                                <label for="email">Email</label>
                                <span class="ss-icon">check</span>
                            </div>

                            <div class="field msg-box">
                                <textarea id="msg" rows="2" placeholder="Your message goes here..."/></textarea>
                                <label for="msg">Msg</label>
                                <span class="ss-icon">check</span>
                            </div>
                            <br>
                            <br>
                            <input class="button" type="submit" value="Send" />
                        </form>
                    </section>
                </div>
            </div>
        </div>
        <jsp:include page="include/footer.jsp"/>
    </body>
</html>
