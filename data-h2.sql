insert into sheet_config (id, sheet_name, range, ic_id_column, ic_name_column, icsemcolumn, day_column, work_time_column, deep_work_blocks_column, dev_time_column, daily_ciccolumn, ws_pro_column)
values (-1, 'Index', 'A2:I', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I');

insert into spreadsheet (id, sheet_config_id, spreadsheet_id, spreadsheeturl)
values (-1, -1, '1uWiPtk60-hV-Ff7fF3m1vv-4UwWFU4D1CCoGLXElhzU',  'https://docs.google.com/spreadsheets/d/1uWiPtk60-hV-Ff7fF3m1vv-4UwWFU4D1CCoGLXElhzU') ;

insert into template(id, name, text)
values (-1, 'Default Template', '
<p>Hello!</p>
<p>Please find below the compliance report for ${.now?string["dd-MM-yyyy"]}.</p>
<p>The report is based on 5 main compliance criteria for the Bootcamp:</p>
<ol>
    <li><strong>Attendance</strong>: each IC should work a minimum of 7 hrs every weekday.</li>
    <li><strong>Deep work blocks</strong>: each IC should not stop tracker more than 3 times every weekday.</li>
    <li><strong>WsPro Compliance</strong>: each IC should have 90% focus and 90% intensity every weekday</li>
    <li><strong>Time Allocation</strong>: each IC should work more than 70% of their time in development every weekday</li>
    <li><strong>Worker initiated CiC</strong>: each IC should fill check-in chat with below information every weekday:
        <ul>
            <li>What have you worked on yesterday?</li>
            <li>What are you planning to work on today?</li>
            <li>Do you have any blockers?</li>
            <li>Mark your STATUS as listed in the application</li>
        </ul>
    </li>
</ol>
<table border="1" cellpadding="5">
    <thead>
        <th>IC Name</th>
        <th>7 Hrs per day</th>
        <th>Deep Work Blocks</th>
        <th>Dev Time >70%</th>
        <th>Daily CiC</th>
        <th>Intensity - Focus 90%</th>
    </thead>
    <tbody>
        <#list lines as line>
            <tr>
                <td>${line.icName}</td>
                <td style="background-color: ${line.workTimeCompliant?string("green", "red")}">
                <td style="background-color: ${line.deepWorkBlocksCompliant?string("green", "red")}">
                <td style="background-color: ${line.devTimeCompliant?string("green", "red")}">
                <td style="background-color: ${line.dailyCiCCompliant?string("green", "red")}">
                <td style="background-color: ${line.wsProCompliant?string("green", "red")}">
            </tr>
        </#list>
    </tbody>
</table>
<p>Please let me know in Skype if you''ll have any questions.</p>
<p>Best regards</p>
');

insert into report(id, name, spreadsheet_id, template_id, mail_from, mail_to, mail_subject)
values(-1, 'Default Report Model', -1, -1, 'eudes.lima@aurea.com', 'aeudes@gmail.com', 'Email testing');



