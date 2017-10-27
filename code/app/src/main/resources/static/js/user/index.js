(function ($) {
    $(function () {

        var dialog, form;

        dialog = $( "#user-dialog" ).dialog({
            autoOpen: false,
            height: 400,
            width: 400,
            modal: true,
            buttons: {
                'Create a user': function() {},
                'Cancel': function() {
                    dialog.dialog( "close" );
                }
            },
            close: function() {
                form[0].reset();
            }
        });

        form = dialog.find( "form" ).on( "submit", function( event ) {
            event.preventDefault();
        });

        $.fn.dataTable.ext.buttons.create = {
            text: 'Create',
            action: function () {
                dialog.dialog('open');
            }
        };
        var userTable = $('#user-table').DataTable({
            dom: 'Bfrtip',
            buttons: [
                'create',
                'colvis',
                'excel',
                'print'
            ],
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
                {name: 'dateCreated', targets: 4, searchable: false, data: 'dateCreated'},
                {name: 'lastUpdated', targets: 5, searchable: false, data: 'lastUpdated'}
            ]
        });

        userTable
            .on( 'select', function ( e, dt, type, indexes ) {
                var rowData = userTable.rows( indexes ).data().toArray();
                events.prepend( '<div><b>'+type+' selection</b> - '+JSON.stringify( rowData )+'</div>' );
            } )
            .on( 'deselect', function ( e, dt, type, indexes ) {
                var rowData = userTable.rows( indexes ).data().toArray();
                events.prepend( '<div><b>'+type+' <i>de</i>selection</b> - '+JSON.stringify( rowData )+'</div>' );
            } );
    });
})(jQuery);