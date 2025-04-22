/**
 * 日付設定関連の機能
 */
const dateUtil = {
    adjustDate: function(d) {
        return new Date(d.setMinutes(d.getMinutes() - d.getTimezoneOffset()));
    },
    
    setDateElement: function(e, p, d) {
        e[p] = this.adjustDate(d).toJSON().match(/\d+-\d+-\d+/);
    },
    
    initDateInputLimits: function() {
        const dateElement = document.querySelector('input[name=date]');
        if (!dateElement) return;
        
        const d = new Date();
                
        // minを当日に設定
        d.setTime(Date.now());
        d.setDate(d.getDate());
        this.setDateElement(dateElement, 'min', d);
        
        // maxを一か月後(30日)に設定
        d.setTime(Date.now());
        d.setDate(d.getDate() + 30);
        this.setDateElement(dateElement, 'max', d);
    }
};

/**
 * テーブル表示をカード表示に変換する機能
 */
const tableConverter = {
    convertToCards: function() {
        // 既にカード表示がある場合は処理しない
        if (document.querySelector('.search-results')) {
            return;
        }
        
        const resultTable = document.querySelector('h2 + table');
        if (!resultTable) return;
        
        // データ構造を作成
        const movieData = {};
        let currentDate = '';
        let currentCinema = '';
        let currentMovie = '';
        
        // テーブルの行を処理
        const rows = resultTable.querySelectorAll('tbody tr');
        for (let i = 0; i < rows.length; i++) {
            const row = rows[i];
            const cells = row.querySelectorAll('td, th');
            
            if (cells.length === 1) {
                const content = cells[0].textContent.trim();
                // 日付行の場合
                if (cells[0].tagName.toLowerCase() === 'th') {
                    currentDate = content;
                    if (!movieData[currentDate]) {
                        movieData[currentDate] = {};
                    }
                } 
                // 映画館名の場合
                else if (cells[0].colSpan === 5) {
                    currentCinema = content;
                    if (!movieData[currentDate][currentCinema]) {
                        movieData[currentDate][currentCinema] = {};
                    }
                } 
                // 映画名の場合
                else {
                    currentMovie = content;
                    if (!movieData[currentDate][currentCinema][currentMovie]) {
                        movieData[currentDate][currentCinema][currentMovie] = {
                            times: [],
                            prices: {},
                            forms: {}
                        };
                    }
                }
            } 
            // 上映時間、料金、予約ボタンの行
            else if (cells.length >= 3 && currentMovie && currentCinema && currentDate) {
                const time = cells[0].textContent.trim();
                const price = cells[1].textContent.trim();
                const form = cells[2].querySelector('form');
                
                movieData[currentDate][currentCinema][currentMovie].times.push(time);
                movieData[currentDate][currentCinema][currentMovie].prices[time] = price;
                if (form) {
                    movieData[currentDate][currentCinema][currentMovie].forms[time] = form.cloneNode(true);
                }
            }
        }
        
        // カード表示を作成
        const searchResults = document.createElement('div');
        searchResults.className = 'search-results';
        
        for (const date in movieData) {
            const dateSection = document.createElement('div');
            dateSection.className = 'date-section';
            
            const dateHeading = document.createElement('h3');
            dateHeading.className = 'date-heading';
            dateHeading.textContent = date;
            dateSection.appendChild(dateHeading);
            
            for (const cinema in movieData[date]) {
                const cinemaSection = document.createElement('div');
                cinemaSection.className = 'cinema-section';
                
                const cinemaName = document.createElement('h4');
                cinemaName.className = 'cinema-name';
                cinemaName.textContent = cinema;
                cinemaSection.appendChild(cinemaName);
                
                const movieCards = document.createElement('div');
                movieCards.className = 'movie-cards';
                
                for (const movie in movieData[date][cinema]) {
                    const movieCard = document.createElement('div');
                    movieCard.className = 'movie-card';
                    
                    const movieTitle = document.createElement('div');
                    movieTitle.className = 'movie-title';
                    movieTitle.textContent = movie;
                    movieCard.appendChild(movieTitle);
                    
                    const movieDetails = document.createElement('div');
                    movieDetails.className = 'movie-details';
                    
                    const timeSlots = document.createElement('div');
                    timeSlots.className = 'time-slots';
                    
                    // 時間スロットを追加
                    for (const time of movieData[date][cinema][movie].times) {
                        const timeSlot = document.createElement('div');
                        timeSlot.className = 'time-slot';
                        timeSlot.textContent = time;
                        timeSlots.appendChild(timeSlot);
                    }
                    
                    movieDetails.appendChild(timeSlots);
                    
                    // 料金情報
                    const priceInfo = document.createElement('div');
                    priceInfo.className = 'price-info';
                    // 最初の時間の料金を使用
                    const firstTime = movieData[date][cinema][movie].times[0];
                    priceInfo.textContent = movieData[date][cinema][movie].prices[firstTime];
                    movieDetails.appendChild(priceInfo);
                    
                    // 予約フォーム
                    if (movieData[date][cinema][movie].forms[firstTime]) {
                        const form = movieData[date][cinema][movie].forms[firstTime];
                        const button = form.querySelector('button');
                        if (button) {
                            button.className = 'reservation-btn';
                            button.textContent = '予約する';
                        }
                        movieDetails.appendChild(form);
                    }
                    
                    movieCard.appendChild(movieDetails);
                    movieCards.appendChild(movieCard);
                }
                
                cinemaSection.appendChild(movieCards);
                dateSection.appendChild(cinemaSection);
            }
            
            searchResults.appendChild(dateSection);
        }
        
        // テーブルをカード表示に置き換え
        resultTable.parentNode.replaceChild(searchResults, resultTable);
    }
};

/**
 * 広告表示を管理するモジュール
 */
const promoManager = {
    // 表示済み広告のIDを記録する配列
    displayedAdIds: [],
    
    // 広告データ (JSPから提供される)
    promoAds: [],
    
    // 広告データを設定
    setPromoAds: function(ads) {
        this.promoAds = ads;
    },
    
    // 広告エリアの初期化
    initializePromoAreas: function() {
        const promoAreas = document.querySelectorAll('.promo-area');
        if (promoAreas.length === 0) return;
        
        // 各広告エリアについて処理
        promoAreas.forEach((area, index) => {
            // 次に表示する広告を取得
            const nextAd = this.getNextAd();
            if (!nextAd) return;
            
            // 広告コンテンツを生成
            const promoHtml = `
                <div class="promo-content">
                    <img class="promo-image" src="${nextAd.image}" alt="${nextAd.title}のポスター画像" />
                    <div class="promo-info">
                        <div class="promo-title">${nextAd.title}</div>
                        <div class="promo-subtitle">${nextAd.subtitle}</div>
                        <div class="promo-description">${nextAd.description}</div>
                        <div class="promo-release">${nextAd.release}</div>
                    </div>
                </div>
            `;
            
            // HTMLを挿入
            area.innerHTML = promoHtml;
            
            // 広告表示ごとにマージンを調整（交互に配置）
            if (index % 2 === 1) {
                area.style.marginTop = '30px';
            }
        });
    },
    
    // 次に表示する広告を選択する関数
    getNextAd: function() {
        // 広告データがない場合
        if (!this.promoAds || this.promoAds.length === 0) {
            return null;
        }
        
        // まだ表示していない広告があるか確認
        const remainingAds = this.promoAds.filter(ad => !this.displayedAdIds.includes(ad.id));
        
        // 全て表示済みなら最初から表示し直す
        if (remainingAds.length === 0) {
            this.displayedAdIds = []; // リセット
            return this.promoAds[0];
        }
        
        // ランダムに選択
        const randomIndex = Math.floor(Math.random() * remainingAds.length);
        const selectedAd = remainingAds[randomIndex];
        
        // 表示済みに追加
        this.displayedAdIds.push(selectedAd.id);
        
        return selectedAd;
    }
};

/**
 * 予約ボタンの機能を管理するモジュール
 */
const reservationManager = {
    setupReservationButtons: function(isLoggedIn) {
        const reservationButtons = document.querySelectorAll('.reservation-btn');
        
        reservationButtons.forEach(button => {
            button.addEventListener('click', function(event) {
                if (!isLoggedIn) {
                    event.preventDefault(); // フォーム送信をキャンセル
                    
                    // ログインページにリダイレクト（現在のURLをパラメータとして渡す）
                    const currentUrl = encodeURIComponent(window.location.href);
                    window.location.href = 'Login?redirect=' + currentUrl;
                }
                // ログイン済みの場合は、フォームがそのまま送信される
            });
        });
    }
};

/**
 * アプリケーション初期化
 */
document.addEventListener('DOMContentLoaded', function() {
    // 日付入力の制限を設定
    dateUtil.initDateInputLimits();
    
    // テーブルをカード表示に変換
    tableConverter.convertToCards();
    
    // 広告エリアの初期化と表示
    promoManager.initializePromoAreas();
    
    // この関数はJSPで定義済のisLoggedIn変数を使用するため、JSPから呼び出す
    // reservationManager.setupReservationButtons(isLoggedIn);
});

// JSPから呼び出せるようにグローバルに公開
window.movieApp = {
    setPromoAds: function(ads) {
        promoManager.setPromoAds(ads);
    },
    setupReservation: function(isLoggedIn) {
        reservationManager.setupReservationButtons(isLoggedIn);
    }
};