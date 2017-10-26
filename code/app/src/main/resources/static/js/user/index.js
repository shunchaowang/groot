(function ($) {
    $(function () {
        $.fn.dataTable.ext.buttons.custom = {
            text: 'Create',
            action: function () {
                var dialog = $( "#user-dialog" ).dialog({
                    autoOpen: false,
                    height: 400,
                    width: 350,
                    modal: true,
                    buttons: {
                        Cancel: function() {
                            dialog.dialog( "close" );
                        }
                    },
                    close: function() {
                        form[ 0 ].reset();
                    }
                });
                dialog.open('open');
            }
        };
        var userTable = $('#user-table').DataTable({
            dom: 'Bfrtip',
            buttons: [
                'colvis',
                'excel',
                'print',
                'custom'
            ],
            'processing': true,
            'serverSide': true,
            'paging': true,
            "paginationType": "full_numbers",
            "order": [[0, "desc"]],
            'ajax': {
                'url': listUserUrl,
                'type': "GET",
                'dataType': 'json'
            },

            // MUST HAVE DATA ON COLUMNDEFS IF SERVER RESPONSE IS JSON ARRAY!!!
            'columnDefs': [
                {'name': 'id', 'targets': 0, 'visible': false, 'data': 'id'},
                {
                    'name': 'username', 'targets': 1, 'data': 'username', 'orderable': false
                },
                {
                    'name': 'firstName', 'targets': 2, 'data': 'firstName', 'searchable': true,
                    'orderable': false
                },
                {
                    'name': 'lastName', 'targets': 3, 'data': 'lastName', 'searchable': true,
                    'orderable': false
                },
                {'name': 'dateCreated', 'targets': 4, 'searchable': false, 'data': 'dateCreated'},
                {'name': 'lastUpdated', 'targets': 5, 'searchable': false, 'data': 'lastUpdated'}
            ]
        });
    });
})(jQuery);