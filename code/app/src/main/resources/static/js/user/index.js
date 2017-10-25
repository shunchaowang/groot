(function ($) {
    $(function () {
        var userTable = $('#user-table').DataTable({
            'processing': true,
            'serverSide': true,
            'paging': true,
            "paginationType": "full_numbers",
            "order": [[0, "desc"]],
            'ajax': {
                'url': "/user/list",
                'type': "GET",
                'dataType': 'json'
            },
            // MUST HAVE DATA ON COLUMNDEFS IF SERVER RESPONSE IS JSON ARRAY!!!
            'columnDefs': [
                {'name': 'id', 'targets': 0, 'visible': false, 'data': 'id'},
                {
                    'name': 'username', 'targets': 1, 'data': 'username',
                    'orderable': false,
                    'render': function (data, type, row) {
                        return '<a href=' + "/user" + '/show/'
                            + row['id'] + '>' + data + '</a>';
                    }
                },
                {
                    'name': 'firstName', 'targets': 2, 'data': 'firstName', 'searchable': false,
                    'orderable': false
                },
                {
                    'name': 'lastName', 'targets': 3, 'data': 'lastName', 'searchable': false,
                    'orderable': false
                },
                {'name': 'dateCreated', 'targets': 4, 'searchable': false, 'data': 'dateCreated'},
                {
                    'name': 'operation', 'targets': 5, 'searchable': false, 'orderable': false,
                    'render': function (data, type, row) {
                        var operations = '<button type="button" name="edit-button" '
                            + 'class="btn btn-default" value="' + row['id'] + '">'
                            + '<spring:message code="action.edit.label"/>'
                            + '</button>';
                        operations += '<button type="button" name="archive-button"'
                            + ' data-username="' + row['username'] + '"'
                            + ' class="btn btn-default" value="' + row['id'] + '">' +
                            "${archiveLabel}"
                            + '</button>';
                        return operations;
                    }
                }
            ]
        });
    });
})(jQuery);