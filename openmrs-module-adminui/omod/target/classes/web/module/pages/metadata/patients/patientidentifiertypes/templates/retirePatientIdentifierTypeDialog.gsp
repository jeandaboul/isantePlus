<div class="dialog-header">
    <h3>${ui.message('adminui.patientIdentifierType.retire.title')}</h3>
</div>
<div class="dialog-content">
    <h4>
        {{ '${ui.message("adminui.retire")}'.replace('{0}', patientIdentifierType.name) }}
    </h4>
    <p>
        ${ui.message("general.reason")}: <input type="text" ng-model="reason" placeholder="${ ui.message("emr.optional") }"/>
    </p>
    <br/>
    <div>
        <button class="confirm right" ng-click="confirm(reason)">${ ui.message("uicommons.confirm") }</button>
        <button class="cancel" ng-click="closeThisDialog()">${ ui.message("uicommons.cancel") }</button>
    </div>
</div>