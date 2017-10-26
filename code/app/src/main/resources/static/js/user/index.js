(function ($) {
    $(function () {
        var userTable = $('#user-table').DataTable({
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
            dom: 'Bfrtip',        // element order: NEEDS BUTTON CONTAINER (B) ****
            select: 'single',     // enable single row selection
            responsive: true,     // enable responsiveness
            altEditor: true,      // Enable altEditor ****
            buttons: [
                {
                text: 'Add',
                name: 'add'        // DO NOT change name
                },
                {
                    extend: 'selected', // Bind to Selected row
                    text: 'Edit',
                    name: 'edit'        // DO NOT change name
                },
                {
                    extend: 'selected', // Bind to Selected row
                    text: 'Delete',
                    name: 'delete'      // DO NOT change name
                }
            ],
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