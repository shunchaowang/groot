(function ($) {
    $(function () {

        var dialog, form, userTable;

        dialog = $("#user-dialog").dialog({
            autoOpen: false,
            height: 550,
            width: 400,
            modal: true,
            buttons: {
                'Create a user': function () {
                },
                'Cancel': function () {
                    dialog.dialog("close");
                }
            },
            close: function () {
                form[0].reset();
            }
        });

        form = dialog.find("form").on("submit", function (event) {
            event.preventDefault();
        });

        // function to get selected row data and put into the dialog
        function populateDialog() {
            var user = userTable.rows({selected: true}).data()[0];
            $('#username').val(user.username);
            $('#firstName').val(user.firstName);
            $('#lastName').val(user.lastName);
            $('#description').val(user.description);
        }

        $.fn.dataTable.ext.buttons.create = {
            className: 'btn-create',
            text: 'Create',
            action: function () {
                dialog.dialog('option', 'title', 'Create User');
                dialog.dialog('open');
            }
        };
        $.fn.dataTable.ext.buttons.edit = {
            className: 'btn-edit',
            text: 'Edit',
            action: function () {
                dialog.dialog('option', 'title', 'Edit User');
                dialog.dialog('open');
                populateDialog();
            }
        };
        $.fn.dataTable.ext.buttons.delete = {
            className: 'btn-delete',
            text: 'Delete',
            action: function () {
                dialog.dialog('option', 'title', 'Delete User');
                dialog.dialog('open');
                populateDialog();
                $('#username').prop('disabled', true);
            }
        };

        userTable = $('#user-table').DataTable({
            initComplete: function() {
                var api = this.api();
                api.buttons('.btn-edit').disable();
                api.buttons('.btn-delete').disable();
            },
            language: {
                url: tableLangUrl
            },
            dom: 'lBfrtip',
            buttons: [
                'create',
                'edit',
                'delete',
                'colvis',
                'excel',
                'print',
                'pdf'
            ],
            responsive: true,
            select: true,

            processing: true,
            serverSide: true,
            paging: true,
            paginationType: 'full_numbers',
            order: [[0, "desc"]],
            ajax: {
                'url': listUserUrl,
                'type': "get",
                'dataType': 'json'
            },

            // MUST HAVE DATA ON COLUMNDEFS IF SERVER RESPONSE IS JSON ARRAY!!!
            columnDefs: [
                {name: 'id', targets: 0, visible: false, data: 'id'},
                {name: 'username', targets: 1, data: 'username', orderable: false},
                {name: 'firstName', targets: 2, data: 'firstName', searchable: true, orderable: false},
                {name: 'lastName', targets: 3, data: 'lastName', searchable: true, orderable: false},
                {name: 'description', targets: 4, data: 'description', searchable: true, orderable: false},
                {name: 'dateCreated', targets: 5, searchable: false, data: 'dateCreated'},
                {name: 'lastUpdated', targets: 6, searchable: false, data: 'lastUpdated'}
            ]
        });

        /**
         * because language.url is an ajax call, the table is initialized async, cannot set button options here.
         * have to use initComplete callback.
         * */
        // userTable.buttons([1,2]).disable(); // select multi buttons by index array
        // userTable.button(1).disable(); // select single button by index
        // userTable.buttons('.btn-edit').disable();
        // userTable.buttons('.btn-delete').disable();
        userTable
            .on('select', function (e, dt, type, indexes) {
                var rowData = userTable.rows(indexes).data().toArray();
                console.log('<div><b>' + type + ' selection</b> - ' + JSON.stringify(rowData) + '</div>');
                // userTable.buttons([1,2]).enable();
                userTable.buttons('.btn-edit').enable();
                userTable.buttons('.btn-delete').enable();
            })
            .on('deselect', function (e, dt, type, indexes) {
                var rowData = userTable.rows(indexes).data().toArray();
                console.log('<div><b>' + type + ' <i>de</i>selection</b> - ' + JSON.stringify(rowData) + '</div>');
                // userTable.buttons([1,2]).disable();
                userTable.buttons('.btn-edit').disable();
                userTable.buttons('.btn-delete').disable();
            });
    });
})(jQuery);