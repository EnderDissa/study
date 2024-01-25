<%!
    String tableRowToHtml(TableRow tableRow) {
        if (tableRow == null) return "";
        return "<tr>" +
                "<td>" + tableRow.getX() + "</td>" +
                "<td>" + tableRow.getY() + "</td>" +
                "<td>" + tableRow.getR() + "</td>" +
                "<td>" + tableRow.getClientDate() + "</td>" +
                "<td>" + tableRow.getScriptWorkingTime() + " ms</td>" +
                "<td>" + tableRow.isHit() + "</td>" +
                "</tr>";
    }
%>

<%@ page import="java.util.ArrayList" %>
<%@ page import="com.enderdissa.table.TableRow" %>
<%@ page import="com.enderdissa.table.Table" %>
<jsp:useBean id="table" scope="session" beanName="com.enderdissa.table.Table" type="com.enderdissa.table.Table"/>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="pics/pic2.png">
    <title>ВЕБ-2 Берман Д.К.</title>
    <link rel="stylesheet" href="css/main.css">
    </head>
<body>
    <div id="page">
            <header class="header">
                <div class="container">
                    <div class="header_title">
                        Берман Денис | @enderdissa | P3233
                    </div>
                </div>
            </header>

            <main class="main">
                <div class="container">
                    <h1 class="main_title">WEB №2, вариант 123123</h1>
                    <div class="main_row">
                        <div class="main_left">
                            <div id="calculator" style="width: 600px; height: 600px;"></div>
                            <script src="https://www.desmos.com/api/v1.6/calculator.js?apiKey=dcb31709b452b1cf9dc26972add0fda6"></script>
                        </div>
                        <div class="main_right">
                            <form id="form">
                                <div class="form_row">
                                <div class="x-button" >
                                    <label id="x-label">выберите X: </label><br />
                                    <button class="x" type="button" value=-3>-3</button>
                                    <button class="x" type="button" value=-2>-2</button>
                                    <button class="x" type="button" value=-1>-1</button>
                                    <button class="x" type="button" value=0>0</button>
                                    <button class="x" type="button" value=1>1</button>
                                    <button class="x" type="button" value=2>2</button>
                                    <button class="x" type="button" value=3>3</button>
                                    <button class="x" type="button" value=4>4</button>
                                    <button class="x" type="button" value=5>5</button>
                                </div>
                                </div>

                                <div class="y-input">
                                    <label id="y-label">введите Y: </label>
                                    <div class="form_row">
                                    <input class="y" id="y-value" name="y" type="text" placeholder="Y (-5, 3)">
                                    </div>
                                </div>
                                <br/>

                                <div class="r-input" >
                                    <label id="r-label"> введите R: </label>
                                    <div class="form_row">
                                    <input class="r" id="r-value" name="r" type="text" placeholder="R (2, 5)">
                                    </div>
                                </div>
                                <br />
                                <div class="form_row">
                                <button class="submit_btn" type="button" id="submit-button">отправить</button>
                                <button type="button" class="clear_btn" id="clear-button">очистить</button>
                                </div>
                            </form>
                        </div>
                        <div class = "result">
                            <table class="results-table" id="results-table">
                                <thead>
                                <tr>
                                    <th width="10%">X</th>
                                    <th width="10%">Y</th>
                                    <th width="5%">R</th>
                                    <th width="30%">дата</th>
                                    <th width="5%">затрачено времени (нс)</th>
                                    <th width="5%">результат</th>
                                </tr>
                                </thead>
                                <tbody id="results-content">
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </main>
    </div>
    <script src="https://code.jquery.com/jquery-2.2.4.min.js"></script>
    <script src="js/main.js"></script>
    <script src="js/graph.js"></script>
    <script src="js/communication.js"></script>
    <script src="js/validator.js"></script>
    <script src="js/tableWorker.js"></script>
    <footer class="footer">
        <div class="container">
            <div class="footer_text">
                <a class="footer__title" href="https://se.ifmo.ru/courses/web">
                    2023 / ITMO
                </a>
            </div>
        </div>
    </footer>
</body>
</html>