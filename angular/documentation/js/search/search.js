(function(compodoc) {
    var usePushState = (typeof history.pushState !== 'undefined'),

    // DOM Elements
        $body = $('body'),
        $searchResults,
        $searchInput,
        $searchList,
        $searchTitle,
        $searchResultsCount,
        $searchQuery,
        $mainContainer,
        $xsMenu;

    // Throttle search
    function throttle(fn, wait) {
        var timeout;

        return function() {
            var ctx = this, args = arguments;
            if (!timeout) {
                timeout = setTimeout(function() {
                    timeout = undefined;
                    fn.apply(ctx, args);
                }, wait);
            }
        };
    }

    function displayResults(res) {
        var noResults = res.count == 0;
        var groups = {};
        $searchResults.toggleClass('no-results', noResults);

        // Clear old results
        $searchList.empty();

        // Display title for research
        $searchResultsCount.text(res.count);
        $searchQuery.text(res.query);

        // Group result by context
        res.results.forEach(function(res) {
            var context = res.title.split(' - ')[0];
            if (typeof groups[context] === 'undefined') {
                groups[context] = {
                    results: [res]
                }
            } else {
                groups[context].results.push(res)
            }
        });

        var sortedGroups = Object.keys(groups).sort();

        for (var i = 0; i < sortedGroups.length; i++) {
            var property = sortedGroups[i];

            var $li = $('<li>', {
                'class': 'search-results.ts-group'
            });
            var finalPropertyLabel = '';
            var propertyLabels = property.split('-');

            if (propertyLabels.length === 2 && propertyLabels[0] !== 'miscellaneous' && propertyLabels[0] !== 'additional') {
                finalPropertyLabel = propertyLabels[0].charAt(0).toUpperCase() + propertyLabels[0].substring(1) + ' - ' + propertyLabels[1].charAt(0).toUpperCase() + propertyLabels[1].substring(1) + ' (' + groups[property].results.length + ')';
            } else if (propertyLabels[0] === 'additional') {
                finalPropertyLabel = 'Additional pages' + ' (' + groups[property].results.length + ')'
            } else {
                finalPropertyLabel = propertyLabels[0].charAt(0).toUpperCase() + propertyLabels[0].substring(1) + ' (' + groups[property].results.length + ')'
            }
            var $groupTitle = $('<h3>', {
                'text': finalPropertyLabel
            });
            $groupTitle.appendTo($li);

            var $ulResults = $('<ul>', {
                'class': 'search-results.ts-list'
            })

            groups[property].results.forEach(function(res) {
                var link = '';
                var $liResult = $('<li>', {
                    'class': 'search-results.ts-item'
                });
                switch (COMPODOC_CURRENT_PAGE_DEPTH) {
                    case 0:
                        link = './';
                        break;
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                        link = '../'.repeat(COMPODOC_CURRENT_PAGE_DEPTH);
                        break;
                };
                var finalResLabel = res.title.split(' - ')[1].charAt(0).toUpperCase() + res.title.split(' - ')[1].substring(1);
                var $link = $('<a>', {
                    'href': link + res.url,
                    'text': finalResLabel
                });
                $link.appendTo($liResult);
                $liResult.appendTo($ulResults);
            });
            $ulResults.appendTo($li);

            $li.appendTo($searchList);
        }
    }

    function launchSearch(q) {
        $body.addClass('with-search');

        if ($xsMenu.css('display') === 'block') {
            $mainContainer.css('height', 'calc(100% - 100px)');
            $mainContainer.css('margin-top', '100px');
        }

        throttle(compodoc.search.query(q, 0, MAX_SEARCH_RESULTS)
        .then(function(results) {
            displayResults(results);
        }), 1000);
    }

    function closeSearch() {
        $body.removeClass('with-search');
        if ($xsMenu.css('display') === 'block') {
            $mainContainer.css('height', 'calc(100% - 50px)');
            $mainContainer.css('margin-top', '50px');
        }
    }

    function bindMenuButton() {
        document.getElementById('btn-menu').addEventListener('click', function() {
            if ($xsMenu.css('display') === 'none') {
                $body.removeClass('with-search');
                $mainContainer.css('height', 'calc(100% - 50px)');
                $mainContainer.css('margin-top', '50px');
            }
            $.each($searchInputs, function(index, item){
                var item = $(item);
                item.val('');
            });
        });
    }

    function bindSearch() {
        // Bind DOM
        $searchInputs        = $('#book-search-input input');

        $searchResults = $('.search-results.ts');
        $searchList         = $searchResults.find('.search-results.ts-list');
        $searchTitle        = $searchResults.find('.search-results.ts-title');
        $searchResultsCount = $searchTitle.find('.search-results.ts-count');
        $searchQuery        = $searchTitle.find('.search-query');
        $mainContainer      = $('.container-fluid');
        $xsMenu             = $('.xs-menu');

        // Launch query based on input content
        function handleUpdate(item) {
            var q = item.val();

            if (q.length == 0) {
                closeSearch();
                window.location.href = window.location.href.replace(window.location.search, '');
            } else {
                launchSearch(q);
            }
        }

        // Detect true content change in search input
        var propertyChangeUnbound = false;

        $.each($searchInputs, function(index, item){
            var item = $(item);
            // HTML5 (IE9 & others)
            item.on('input', function(e) {
                // Unbind propertychange event for IE9+
                if (!propertyChangeUnbound) {
                    $(this).unbind('propertychange');
                    propertyChangeUnbound = true;
                }

                handleUpdate($(this));
            });
            // Workaround for IE < 9
            item.on('propertychange', function(e) {
                if (e.originalEvent.propertyName == 'value') {
                    handleUpdate($(this));
                }
            });
            // Push to history on blur
            item.on('blur', function(e) {
                // Update history state
                if (usePushState) {
                    var uri = updateQueryString('q', $(this).val());
                    if ($(this).val() !== '') {
                        history.pushState({ path: uri }, null, uri);
                    }
                }
            });
        });
    }

    function launchSearchFromQueryString() {
        var q = getParameterByName('q');
        if (q && q.length > 0) {
            // Update search inputs
            $.each($searchInputs, function(index, item){
                var item = $(item);
                item.val(q)
            });
            // Launch search
            launchSearch(q);
        }
    }

    compodoc.addEventListener(compodoc.EVENTS.SEARCH_READY, function(event) {
        bindSearch();

        bindMenuButton();

        launchSearchFromQueryString();
    });

    function getParameterByName(name) {
        var url = window.location.href;
        name = name.replace(/[\[\]]/g, '\\$&');
        var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)', 'i'),
            results = regex.exec(url);
        if (!results) return null;
        if (!results[2]) return '';
        return decodeURIComponent(results[2].replace(/\+/g, ' '));
    }

    function updateQueryString(key, value) {
        value = encodeURIComponent(value);

        var url = window.location.href;
        var re = new RegExp('([?&])' + key + '=.*?(&|#|$)(.*)', 'gi'),
            hash;

        if (re.test(url)) {
            if (typeof value !== 'undefined' && value !== null)
                return url.replace(re, '$1' + key + '=' + value + '$2$3');
            else {
                hash = url.split('#');
                url = hash[0].replace(re, '$1$3').replace(/(&|\?)$/, '');
                if (typeof hash[1] !== 'undefined' && hash[1] !== null)
                    url += '#' + hash[1];
                return url;
            }
        }
        else {
            if (typeof value !== 'undefined' && value !== null) {
                var separator = url.indexOf('?') !== -1 ? '&' : '?';
                hash = url.split('#');
                url = hash[0] + separator + key + '=' + value;
                if (typeof hash[1] !== 'undefined' && hash[1] !== null)
                    url += '#' + hash[1];
                return url;
            }
            else
                return url;
        }
    }
})(compodoc);
